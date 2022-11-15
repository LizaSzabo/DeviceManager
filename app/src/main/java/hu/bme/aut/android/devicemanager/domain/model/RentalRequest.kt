package hu.bme.aut.android.devicemanager.domain.model

data class RentalRequest(
    val id: Long? = null,
    val device: Device? = null,
    val user: User? = null,
    val status: RentalRequestStatus? = RentalRequestStatus.Active,
)

enum class RentalRequestStatus {
    Active, Accepted
}
