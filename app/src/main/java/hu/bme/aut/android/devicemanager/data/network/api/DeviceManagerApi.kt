package hu.bme.aut.android.devicemanager.data.network.api

import hu.bme.aut.android.devicemanager.data.network.model.*
import retrofit2.http.*

interface DeviceManagerApi {

    @POST("sign-up")
    suspend fun createUser(
        @Body signUpRequest: SignUpRequest,
    )

    @POST("sign-up/admin")
    suspend fun createAdmin(
        @Header("Authorization") token: String,
        @Body signUpRequest: SignUpRequest,
    )

    @POST("sign-in")
    suspend fun loginUser(
        @Body loginRequest: LoginNetworkRequest
    ): LoginNetworkResponse


    @GET("device")
    suspend fun getDevices(@Header("Authorization") token: String): List<DeviceListElementResponse>

    @POST("device")
    suspend fun addDevice(
        @Header("Authorization") token: String,
        @Body addDeviceRequest: AddDeviceRequest
    )

    @GET("device/{id}")
    suspend fun getDevice(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): DeviceResponse

    @DELETE("device/{id}")
    suspend fun deleteDevice(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): DeviceDeleteResponse

    @POST("rental")
    suspend fun saveRentalRequest(
        @Header("Authorization") token: String,
        @Body rentalNetworkRequest: RentalNetworkRequest,
    ): RentalNetworkResponse

    @GET("rental")
    suspend fun getRentalRequests(
        @Header("Authorization") token: String,
    ): List<GetRentalRequestResponse>

    @GET("rental/{id}")
    suspend fun getRentalRequest(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): GetRentalRequestIdResponse
}