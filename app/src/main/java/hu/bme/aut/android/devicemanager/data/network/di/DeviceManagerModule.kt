package hu.bme.aut.android.devicemanager.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DeviceManagerModule {


    companion object {
        const val BaseUrl = "http://Devicerenter-env.eba-xkymi3qb.eu-central-1.elasticbeanstalk.com/api/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideDeviceManagerApi(): DeviceManagerApi {
        return retrofit.create(DeviceManagerApi::class.java)
    }
}