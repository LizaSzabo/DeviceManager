package hu.bme.aut.android.devicemanager.domain.model

data class Device(
    val id: String? = null,
    val name: String,
    val state: DeviceRentalState? = DeviceRentalState.Available,
    val calendar: List<Calendar>? = null,
)

enum class DeviceRentalState {
    Rented, Available
}