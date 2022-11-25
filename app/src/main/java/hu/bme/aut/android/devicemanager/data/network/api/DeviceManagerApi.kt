package hu.bme.aut.android.devicemanager.data.network.api

import hu.bme.aut.android.devicemanager.data.network.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DeviceManagerApi {

    @POST("sign-up")
    suspend fun createUser(
        @Body signUpRequest: SignUpRequest,
    )

    @POST("sign-in")
    suspend fun loginUser(
        @Body loginRequest: LoginNetworkRequest
    ): LoginNetworkResponse


    @GET("device")
    suspend fun getDevices(@Header("Authorization") token: String): List<DeviceResponse>

    @POST("device")
    suspend fun addDevice(
        @Header("Authorization") token: String,
        @Body addDeviceRequest: AddDeviceRequest
    )
}