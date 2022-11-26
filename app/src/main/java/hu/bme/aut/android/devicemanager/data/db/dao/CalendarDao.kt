package hu.bme.aut.android.devicemanager.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.devicemanager.data.db.model.RoomCalendar

@Dao
interface CalendarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCalendar(calendar: RoomCalendar)

    @Query("SELECT * FROM calendar WHERE id = :calendarId")
    fun getCalendar(calendarId: String): List<RoomCalendar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCalendars(calendar: Collection<RoomCalendar>)

    @Query("DELETE FROM calendar")
    fun deleteCalendars()

    @Query("DELETE FROM calendar WHERE id = :calendarId")
    fun deleteCalendar(calendarId: String)
}