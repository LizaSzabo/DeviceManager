package hu.bme.aut.android.devicemanager.ui.calendar

import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.activeRents
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.calendar
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.currentUser
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.mockDeviceData
import hu.bme.aut.android.devicemanager.domain.model.ActiveRent
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarPresenter: CalendarPresenter
) : RainbowCakeViewModel<CalendarViewState>(Initial) {

    fun setSelectedDateToRentalRequest(deviceId: Long, startDay: LocalDate, endDate: LocalDate) {
        Log.i("startandendDate: ", startDay.toString() + " " + endDate.toString())


        //TODO: delete old values if any
        activeRents.add(
            ActiveRent(
                id = 0,
                startDate = startDay,
                endDate = endDate,
                currentUser.id
            )
        )

        //TODO: search calendar of device
        calendar = calendar.copy(activeRents = activeRents)

        val deviceData = mockDeviceData.first { it.id == deviceId }
        val index = mockDeviceData.indexOf(deviceData)

        val modifiedDeviceData = deviceData.copy(calendar = calendar)

        mockDeviceData[index] = modifiedDeviceData
    }
}