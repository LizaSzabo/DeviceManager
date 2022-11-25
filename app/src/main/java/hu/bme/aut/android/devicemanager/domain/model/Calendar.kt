package hu.bme.aut.android.devicemanager.domain.model

data class Calendar(
    val id: String,
    val userId: String,
    val username: String,
    val from: String,
    val to: String,
)