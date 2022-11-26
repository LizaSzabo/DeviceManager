package hu.bme.aut.android.devicemanager.domain.interactors

import android.util.Log
import hu.bme.aut.android.devicemanager.data.db.model.RoomCalendar
import hu.bme.aut.android.devicemanager.data.db.model.RoomDevice
import hu.bme.aut.android.devicemanager.data.db.model.RoomListDeviceID
import hu.bme.aut.android.devicemanager.data.db.source.DeviceDataSource
import hu.bme.aut.android.devicemanager.data.network.model.RentalNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.source.DeviceNetworkDataSource
import hu.bme.aut.android.devicemanager.domain.model.ActiveRent
import hu.bme.aut.android.devicemanager.domain.model.Calendar
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.domain.model.DeviceRentalState
import hu.bme.aut.android.devicemanager.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DeviceInteractor @Inject constructor(
    private val deviceNetworkDataSource: DeviceNetworkDataSource,
    private val deviceDataSource: DeviceDataSource,
) {

    suspend fun getDevices(): NetworkResponse<List<Device>> {
        return when (val getDevicesResponse = deviceNetworkDataSource.getDevices()) {
            is NetworkError -> {
                NetworkError(getDevicesResponse.errorMessage)
            }
            is NetworkResult -> {
                val deviceIdList = getDevicesResponse.result.map { RoomListDeviceID(id = it.id) }
                deviceDataSource.saveDeviceIdList(deviceIdList)

                val devices = getDevicesResponse.result.map { Device(id = it.id, name = it.name) }
                NetworkResult(devices)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }

    suspend fun addDevice(deviceName: String): NetworkResponse<String> {
        return when (val addDevicesResponse = deviceNetworkDataSource.addDevice(deviceName)) {
            is NetworkError -> {
                NetworkError(addDevicesResponse.errorMessage)
            }
            is NetworkNoResult -> {
                NetworkResult("success")
            }
            UnknownHostError -> NetworkError("UnknownHostError")
            is NetworkResult -> {
                NetworkResult("success")
            }
        }
    }

    suspend fun getDevice(deviceId: String): NetworkResponse<Device> {
        val getDeviceFromDb = deviceDataSource.getDeviceFromDb(deviceId)
        if (getDeviceFromDb.isNotEmpty()) {
            val calendars = mutableListOf<Calendar>()
            getDeviceFromDb.forEach { roomDevice ->
                roomDevice.calendarIds.forEach { calendarId ->
                    val calendarFromDb = deviceDataSource.getCalendarFromDb(calendarId)
                    if (calendarFromDb.isNotEmpty()) {
                        val calendar =
                            Calendar(
                                id = calendarFromDb[0].id,
                                userId = calendarFromDb[0].userId,
                                username = calendarFromDb[0].userName,
                                from = calendarFromDb[0].from,
                                to = calendarFromDb[0].to,
                            )
                        calendars.add(calendar)
                    }
                }
            }

            val state: DeviceRentalState =
                if (getDeviceFromDb[0].state == "AVAILABLE") DeviceRentalState.Available else DeviceRentalState.Rented
            val device =
                Device(
                    id = getDeviceFromDb[0].id,
                    name = getDeviceFromDb[0].name,
                    state = state,
                    calendar = calendars,
                )
            return NetworkResult(device)
        }
        return when (val getDeviceResponse = deviceNetworkDataSource.getDevice(deviceId)) {
            is NetworkError -> {
                NetworkError(getDeviceResponse.errorMessage)
            }
            is NetworkResult -> {
                val state: DeviceRentalState =
                    if (getDeviceResponse.result.state == "AVAILABLE") DeviceRentalState.Available else DeviceRentalState.Rented
                val device =
                    Device(
                        id = getDeviceResponse.result.id,
                        name = getDeviceResponse.result.name,
                        state = state,
                        calendar = getDeviceResponse.result.calendar
                    )


                Log.i("DisableddatessetupDisableDates", device.toString())
                val calendarIds = getDeviceResponse.result.calendar.map { it.id }
                deviceDataSource.saveDeviceToDb(
                    RoomDevice(
                        id = getDeviceResponse.result.id,
                        name = getDeviceResponse.result.name,
                        state = getDeviceResponse.result.state,
                        calendarIds = calendarIds as ArrayList<String>,
                    )
                )
                getDeviceResponse.result.calendar.forEach { calendar ->
                    deviceDataSource.saveCalendarToDb(
                        RoomCalendar(
                            id = calendar.id,
                            userId = calendar.userId,
                            userName = calendar.username,
                            from = calendar.from,
                            to = calendar.to,
                        )
                    )
                }

                NetworkResult(device)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }


    suspend fun deleteDevice(deviceId: String): NetworkResponse<Boolean> {
        return when (val deleteDeviceResponse = deviceNetworkDataSource.deleteDevice(deviceId)) {
            is NetworkError -> {
                NetworkError(deleteDeviceResponse.errorMessage)
            }
            is NetworkResult -> {
                NetworkResult(deleteDeviceResponse.result.isSuccessful)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }


    suspend fun getActiveRents(deviceId: String): NetworkResponse<List<ActiveRent>> {
        val activeRentsList = mutableListOf<ActiveRent>()
        when (val device = getDevice(deviceId)) {
            is NetworkResult -> {
                val calendars = device.result.calendar
                calendars?.forEach { calendar ->
                    val activeRent =
                        ActiveRent(calendar.from.toLocalDate(), calendar.to.toLocalDate())
                    activeRentsList.add(activeRent)
                }
            }
            is NetworkError -> return NetworkError(device.errorMessage)
            UnknownHostError -> return NetworkError("UnknownHostError")
        }
        return NetworkResult(activeRentsList)
    }

    private fun String.toLocalDate(): LocalDate {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        return LocalDate.parse(this, formatter)
    }
}