package hu.bme.aut.android.devicemanager.data.network.model

data class GetRentalRequestIdResponse(
    val id: String,
    val deviceId: String,
    val deviceName: String,
    val username: String,
    val from: String,
    val to: String,
    val state: String,
    val acceptComment: String,
    val closeComment: String,
)