package hu.bme.aut.android.devicemanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.devicemanager.data.db.dao.CalendarDao
import hu.bme.aut.android.devicemanager.data.db.dao.DeviceDao
import hu.bme.aut.android.devicemanager.data.db.dao.ListDeviceDao
import hu.bme.aut.android.devicemanager.data.db.dao.RentalDao
import hu.bme.aut.android.devicemanager.data.db.model.RoomCalendar
import hu.bme.aut.android.devicemanager.data.db.model.RoomDevice
import hu.bme.aut.android.devicemanager.data.db.model.RoomListDeviceID
import hu.bme.aut.android.devicemanager.data.db.model.RoomRentalRequest

@Database(
    exportSchema = false,
    version = 1,
    entities = [
        RoomCalendar::class,
        RoomDevice::class,
        RoomRentalRequest::class,
        RoomListDeviceID::class,
    ]
)
@TypeConverters(ArrayListConverter::class)
abstract class DeviceManagerDatabase : RoomDatabase() {

    abstract fun calendarDao(): CalendarDao

    abstract fun deviceDao(): DeviceDao

    abstract fun listDeviceDao(): ListDeviceDao

    abstract fun rentalDao(): RentalDao
}