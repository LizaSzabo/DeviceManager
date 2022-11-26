package hu.bme.aut.android.devicemanager.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.devicemanager.data.db.model.RoomListDeviceID

@Dao
interface ListDeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDeviceIds(device: Collection<RoomListDeviceID>)

    @Query("SELECT * FROM device")
    fun getDeviceIds(): List<RoomListDeviceID>
}