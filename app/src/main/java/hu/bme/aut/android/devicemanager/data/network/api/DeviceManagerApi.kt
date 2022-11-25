package hu.bme.aut.android.devicemanager.data.network.api

import hu.bme.aut.android.devicemanager.data.network.model.LoginNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.model.LoginNetworkResponse
import hu.bme.aut.android.devicemanager.data.network.model.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET
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
}