package hu.bme.aut.android.devicemanager.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.devicemanager.data.db.dao.CalendarDao
import hu.bme.aut.android.devicemanager.data.db.dao.DeviceDao
import hu.bme.aut.android.devicemanager.data.db.dao.ListDeviceDao
import hu.bme.aut.android.devicemanager.data.db.dao.RentalDao
import hu.bme.aut.android.devicemanager.data.db.source.DeviceDataSource
import hu.bme.aut.android.devicemanager.data.db.source.RentalDataSource
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DeviceManagerDatabaseModule {

    @Provides
    @Singleton
    fun provideDeviceManagerDatabase(@ApplicationContext context: Context): DeviceManagerDatabase {
        return Room.inMemoryDatabaseBuilder(context, DeviceManagerDatabase::class.java)
            .build()
    }


    @Provides
    @Singleton
    fun provideCalendarDao(deviceManagerDatabase: DeviceManagerDatabase): CalendarDao {
        return deviceManagerDatabase.calendarDao()
    }

    @Provides
    @Singleton
    fun provideDeviceDao(deviceManagerDatabase: DeviceManagerDatabase): DeviceDao {
        return deviceManagerDatabase.deviceDao()
    }

    @Provides
    @Singleton
    fun provideListDeviceDao(deviceManagerDatabase: DeviceManagerDatabase): ListDeviceDao {
        return deviceManagerDatabase.listDeviceDao()
    }

    @Provides
    @Singleton
    fun provideRentalDao(deviceManagerDatabase: DeviceManagerDatabase): RentalDao {
        return deviceManagerDatabase.rentalDao()
    }

    @Provides
    @Singleton
    fun provideRentalDataSource(rentalDao: RentalDao): RentalDataSource = RentalDataSource(
        rentalDao = rentalDao,
    )

    @Provides
    @Singleton
    fun provideDeviceDataSource(
        deviceDao: DeviceDao,
        calendarDao: CalendarDao,
        listDeviceDao: ListDeviceDao
    ): DeviceDataSource =
        DeviceDataSource(
            deviceDao = deviceDao,
            calendarDao = calendarDao,
            listDeviceDao = listDeviceDao,
        )
}