package hu.bme.aut.android.devicemanager.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rentalRequest")
data class RoomRentalRequest(
    @PrimaryKey
    val id: String,
    val deviceId: String,
    val deviceName: String,
    val username: String,
    val from: String,
    val to: String,
    val state: String,
    val acceptComment: String,
    val closeComment: String,
)