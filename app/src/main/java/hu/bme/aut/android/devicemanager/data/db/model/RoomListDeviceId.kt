package hu.bme.aut.android.devicemanager.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listDeviceId")
data class RoomListDeviceID(
    @PrimaryKey
    val id: String,
)