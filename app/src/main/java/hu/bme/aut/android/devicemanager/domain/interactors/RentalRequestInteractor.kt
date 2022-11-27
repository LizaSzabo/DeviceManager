package hu.bme.aut.android.devicemanager.domain.interactors

import hu.bme.aut.android.devicemanager.data.network.model.RentalAcceptRequest
import hu.bme.aut.android.devicemanager.data.network.model.RentalNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.model.RentalTakeBackRequest
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
            UnknownHostError -> NetworkError("NoNetworkError")
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
            UnknownHostError -> NetworkError("NoNetworkError")
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
            UnknownHostError -> NetworkError("NoNetworkError")
        }
    }

    suspend fun acceptRentalRequest(rentalRequestId: String, comment: String, state: String):
            NetworkResponse<Boolean> {
        val rentalRequestAccept = RentalAcceptRequest(state = state, comment = comment)
        return when (val rentalRequestAcceptResponse =
            rentalNetworkDataSource.acceptRentalRequest(
                rentalRequestId = rentalRequestId,
                acceptRequest = rentalRequestAccept
            )) {
            is NetworkResult -> {
                NetworkResult(true)
            }
            is NetworkError -> {
                NetworkError(rentalRequestAcceptResponse.errorMessage)
            }
            UnknownHostError -> NetworkError("NoNetworkError")
        }
    }

    suspend fun takeBackRentalRequest(rentalRequestId: String, comment: String, state: String):
            NetworkResponse<Boolean> {
        val rentalTakeBackRequest = RentalTakeBackRequest(state = state, comment = comment)
        return when (val rentalRequestTakeBackResponse =
            rentalNetworkDataSource.takeBackRentalRequest(
                rentalRequestId = rentalRequestId,
                takeBackRequest = rentalTakeBackRequest,
            )) {
            is NetworkResult -> {
                NetworkResult(true)
            }
            is NetworkError -> {
                NetworkError(rentalRequestTakeBackResponse.errorMessage)
            }
            UnknownHostError -> NetworkError("NoNetworkError")
        }
    }
}