package hu.bme.aut.android.devicemanager.domain.interactors

import hu.bme.aut.android.devicemanager.data.network.model.RentalNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.source.RentalNetworkDataSource
import hu.bme.aut.android.devicemanager.domain.model.RentalRequest
import hu.bme.aut.android.devicemanager.domain.model.RentalRequestStatus
import hu.bme.aut.android.devicemanager.util.NetworkError
import hu.bme.aut.android.devicemanager.util.NetworkResponse
import hu.bme.aut.android.devicemanager.util.NetworkResult
import hu.bme.aut.android.devicemanager.util.UnknownHostError
import javax.inject.Inject

class RentalRequestInteractor @Inject constructor(
    private val rentalNetworkDataSource: RentalNetworkDataSource,
) {
    suspend fun saveRentalRequest(
        deviceId: String,
        from: String,
        to: String
    ): NetworkResponse<String> {
        val rentalRequest = RentalNetworkRequest(deviceId = deviceId, from = from, to = to)
        return when (val saveRentalRequestResponse =
            rentalNetworkDataSource.saveRentalRequest(rentalRequest)) {
            is NetworkError -> {
                NetworkError(saveRentalRequestResponse.errorMessage)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
            is NetworkResult -> {
                NetworkResult(saveRentalRequestResponse.result.id)
            }
        }
    }

    suspend fun getRentalRequests(): NetworkResponse<List<RentalRequest>> {
        return when (val getRentalRequestsResponse = rentalNetworkDataSource.getRentalRequests()) {
            is NetworkError -> {
                NetworkError(getRentalRequestsResponse.errorMessage)
            }
            is NetworkResult -> {
                val rentalRequests =
                    getRentalRequestsResponse.result.map {
                        RentalRequest(
                            id = it.id,
                            deviceName = it.deviceName,
                            state = when (it.state) {
                                "ACTIVE" -> RentalRequestStatus.Active
                                "ACCEPTED" -> RentalRequestStatus.Accepted
                                "CLOSED" -> RentalRequestStatus.Closed
                                else -> RentalRequestStatus.Active
                            },
                        )
                    }
                NetworkResult(rentalRequests)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }

    suspend fun getRentalRequest(rentalRequestId: String): NetworkResponse<RentalRequest> {
        return when (val getRentalRequestResponse =
            rentalNetworkDataSource.getRentalRequest(rentalRequestId)) {
            is NetworkError -> {
                NetworkError(getRentalRequestResponse.errorMessage)
            }
            is NetworkResult -> {
                val rentalRequest = RentalRequest(
                    id = getRentalRequestResponse.result.id,
                    deviceId = getRentalRequestResponse.result.deviceId,
                    deviceName = getRentalRequestResponse.result.deviceName,
                    username = getRentalRequestResponse.result.username,
                    from = getRentalRequestResponse.result.from,
                    to = getRentalRequestResponse.result.to,
                    state = when (getRentalRequestResponse.result.state) {
                        "ACTIVE" -> RentalRequestStatus.Active
                        "ACCEPTED" -> RentalRequestStatus.Accepted
                        "CLOSED" -> RentalRequestStatus.Closed
                        else -> RentalRequestStatus.Active
                    },
                    closeComment = getRentalRequestResponse.result.closeComment,
                    acceptComment = getRentalRequestResponse.result.acceptComment,
                )

                NetworkResult(rentalRequest)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }
}