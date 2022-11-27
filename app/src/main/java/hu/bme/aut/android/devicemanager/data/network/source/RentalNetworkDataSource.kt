package hu.bme.aut.android.devicemanager.data.network.source

import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.token
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import hu.bme.aut.android.devicemanager.data.network.model.RentalAcceptRequest
import hu.bme.aut.android.devicemanager.data.network.model.RentalNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.model.RentalTakeBackRequest
import hu.bme.aut.android.devicemanager.util.apiCall

class RentalNetworkDataSource(
    private val deviceManagerApi: DeviceManagerApi
) {
    suspend fun saveRentalRequest(rentalRequest: RentalNetworkRequest) = apiCall {
        deviceManagerApi.saveRentalRequest(token, rentalRequest)
    }

    suspend fun getRentalRequests() = apiCall {
        deviceManagerApi.getRentalRequests(token)
    }

    suspend fun getRentalRequest(rentalRequestId: String) = apiCall {
        deviceManagerApi.getRentalRequest(token, rentalRequestId)
    }

    suspend fun acceptRentalRequest(rentalRequestId: String, acceptRequest: RentalAcceptRequest) =
        apiCall {
            deviceManagerApi.acceptRentalRequest(token, rentalRequestId, acceptRequest)
        }

    suspend fun takeBackRentalRequest(
        rentalRequestId: String,
        takeBackRequest: RentalTakeBackRequest
    ) =
        apiCall {
            deviceManagerApi.takeBackRentalRequest(token, rentalRequestId, takeBackRequest)
        }
}