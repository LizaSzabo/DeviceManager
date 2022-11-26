package hu.bme.aut.android.devicemanager.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "device")
data class RoomDevice(
    @PrimaryKey
    val id: String,
    val name: String,
    val state: String,
    val calendarIds: ArrayList<String>,
)