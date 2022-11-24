package hu.bme.aut.android.devicemanager.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeviceManagerModule {

    @Singleton
    @Provides
    fun provideDeviceManagerApi(@RetrofitModule.Client retrofit: Retrofit): DeviceManagerApi {
        return retrofit.create(DeviceManagerApi::class.java)
    }
}