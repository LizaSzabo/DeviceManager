package hu.bme.aut.android.devicemanager.domain.model

data class RentalRequest(
    val id: String,
    val deviceId: String? = null,
    val deviceName: String,
    val username: String? = null,
    val from: String? = null,
    val to: String? = null,
    val state: RentalRequestStatus = RentalRequestStatus.Active,
    val acceptComment: String? = null,
    val closeComment: String? = null,
)

enum class RentalRequestStatus {
    Active, Accepted, Closed
}
