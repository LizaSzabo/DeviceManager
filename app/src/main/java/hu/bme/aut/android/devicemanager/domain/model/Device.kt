package hu.bme.aut.android.devicemanager.domain.model

data class Device(
    val id: Long? = null,
    val name: String,
    val qrCode: String? = null,
    val state: DeviceRentalState? = DeviceRentalState.Available
)

enum class DeviceRentalState {
    Rented, Available
}