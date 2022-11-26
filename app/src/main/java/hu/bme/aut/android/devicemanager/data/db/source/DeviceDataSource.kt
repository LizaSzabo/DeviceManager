package hu.bme.aut.android.devicemanager.data.db.source

import hu.bme.aut.android.devicemanager.data.db.dao.CalendarDao
import hu.bme.aut.android.devicemanager.data.db.dao.DeviceDao
import hu.bme.aut.android.devicemanager.data.db.model.RoomCalendar
import hu.bme.aut.android.devicemanager.data.db.model.RoomDevice

class DeviceDataSource(
    private val deviceDao: DeviceDao,
    private val calendarDao: CalendarDao,
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

    fun deleteAllDevices() {
        deviceDao.deleteDevices()
    }

    fun deleteAllCalendars() {
        calendarDao.deleteCalendars()
    }
}