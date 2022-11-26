package hu.bme.aut.android.devicemanager.domain.model

data class RentalRequest(
    val id: String,
    val deviceName: String,
    val user: User? = null,
    val status: RentalRequestStatus? = RentalRequestStatus.Active,
)

enum class RentalRequestStatus {
    Active, Accepted, Closed
}
