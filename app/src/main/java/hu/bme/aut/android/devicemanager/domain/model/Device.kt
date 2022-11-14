package hu.bme.aut.android.devicemanager.domain.model

import android.graphics.Bitmap

data class Device(
    val id: Long? = null,
    val name: String,
    val qrCode: Bitmap? = null,
)