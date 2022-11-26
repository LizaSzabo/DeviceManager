package hu.bme.aut.android.devicemanager.data.network.model

data class RentalNetworkRequest(
    val deviceId: String,
    val from : String,
    val to: String,
)