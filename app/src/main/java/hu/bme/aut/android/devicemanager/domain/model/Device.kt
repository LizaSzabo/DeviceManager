package hu.bme.aut.android.devicemanager.domain.model

data class Device(
    val id: String? = null,
    val name: String,
    val qrCode: String? = null, //nem kell
    val state: DeviceRentalState? = DeviceRentalState.Available,
    val calendar: Calendar? = null,
)

enum class DeviceRentalState {
    Rented, Available
}