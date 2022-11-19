package hu.bme.aut.android.devicemanager.domain.model

import java.util.*

data class ActiveRent(
    val id: Long? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val userId: Long? = null,
)
