package hu.bme.aut.android.devicemanager.domain.model

import java.time.LocalDate

data class ActiveRent(
    val id: Long? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val userId: Long? = null,
)
