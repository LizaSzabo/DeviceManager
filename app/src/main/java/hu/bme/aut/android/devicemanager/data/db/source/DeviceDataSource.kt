package hu.bme.aut.android.devicemanager.data.db.source

import hu.bme.aut.android.devicemanager.data.db.dao.CalendarDao
import hu.bme.aut.android.devicemanager.data.db.dao.DeviceDao
import hu.bme.aut.android.devicemanager.data.db.dao.ListDeviceDao
import hu.bme.aut.android.devicemanager.data.db.model.RoomCalendar
import hu.bme.aut.android.devicemanager.data.db.model.RoomDevice
import hu.bme.aut.android.devicemanager.data.db.model.RoomListDeviceID

class DeviceDataSource(
    private val deviceDao: DeviceDao,
    private val calendarDao: CalendarDao,
    private val listDeviceDao: ListDeviceDao,
) {

    fun saveDeviceToDb(roomDevice: RoomDevice) {
        deviceDao.saveDevice(roomDevice)
    }

    fun saveCalendarToDb(roomCalendar: RoomCalendar) {
        calendarDao.saveCalendar(roomCalendar)
    }

    fun getDeviceFromDb(deviceId: String): List<RoomDevice> {
        return deviceDao.getDevice(deviceId)
    }

    fun getCalendarFromDb(calendarId: String): List<RoomCalendar> {
        return calendarDao.getCalendar(calendarId)
    }

    fun saveDeviceIdList(roomListDeviceID: List<RoomListDeviceID>) {
        listDeviceDao.saveDeviceIds(roomListDeviceID)
    }
}