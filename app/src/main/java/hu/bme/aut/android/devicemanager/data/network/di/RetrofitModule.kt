package hu.bme.aut.android.devicemanager.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Client
    @Singleton
    @Provides
    fun provideOkHttpClient(
        okHttpBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        return okHttpBuilder.build()
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Client


    @Client
    @Singleton
    @Provides
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @Client okHttpClient: OkHttpClient
    ): Retrofit {
        return retrofitBuilder.client(okHttpClient).build()
    }


}