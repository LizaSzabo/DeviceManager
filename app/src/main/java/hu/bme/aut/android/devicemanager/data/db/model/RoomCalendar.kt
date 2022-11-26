package hu.bme.aut.android.devicemanager.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calendar")
data class RoomCalendar(
    @PrimaryKey
    val id: String,
    val userId: String,
    val userName: String,
    val from: String,
    val to: String,
)