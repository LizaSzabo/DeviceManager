package hu.bme.aut.android.devicemanager.domain.model

data class Calendar(
    val id: Long? = null,
    val deviceId: Long? = null,
    val activeRents: List<ActiveRent> = listOf(),
)