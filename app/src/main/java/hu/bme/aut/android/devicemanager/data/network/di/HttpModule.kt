package hu.bme.aut.android.devicemanager.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class HttpModule {

    companion object {
        const val BaseUrl = "http://Devicerenter-env.eba-xkymi3qb.eu-central-1.elasticbeanstalk.com/api"
    }

    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
    }

}