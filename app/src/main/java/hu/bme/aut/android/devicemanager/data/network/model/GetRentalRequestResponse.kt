package hu.bme.aut.android.devicemanager.data.network.model

data class GetRentalRequestResponse(
    val id: String,
    val deviceName: String,
    val state: String,
)