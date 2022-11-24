package hu.bme.aut.android.devicemanager.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import hu.bme.aut.android.devicemanager.data.network.source.DeviceNetworkDataSource
import hu.bme.aut.android.devicemanager.data.network.source.LoginNetworkDataSource
import hu.bme.aut.android.devicemanager.data.network.source.RentalNetworkDataSource
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideLoginNetworkDataSource(
        deviceManagerApi: DeviceManagerApi
    ): LoginNetworkDataSource = LoginNetworkDataSource(deviceManagerApi)

    @Singleton
    @Provides
    fun provideDeviceNetworkDataSource(
        deviceManagerApi: DeviceManagerApi
    ): DeviceNetworkDataSource = DeviceNetworkDataSource(deviceManagerApi)


    @Singleton
    @Provides
    fun provideRentalNetworkDataSource(
        deviceManagerApi: DeviceManagerApi
    ): RentalNetworkDataSource = RentalNetworkDataSource(deviceManagerApi)
}