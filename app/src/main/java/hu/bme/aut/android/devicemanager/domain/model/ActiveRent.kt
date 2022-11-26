package hu.bme.aut.android.devicemanager.domain.model

import java.time.LocalDate

data class ActiveRent(
    val startDate: LocalDate,
    val endDate: LocalDate,
)
