package hu.bme.aut.android.devicemanager.data.network.model

import hu.bme.aut.android.devicemanager.domain.model.Calendar

data class DeviceResponse(
    val id: String,
    val name: String,
    val state: String,
    val calendar: List<Calendar>
)
