package hu.bme.aut.android.devicemanager.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.devicemanager.data.db.model.RoomDevice

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDevice(device: RoomDevice)

    @Query("SELECT * FROM device WHERE id = :deviceId")
    fun getDevice(deviceId: String): List<RoomDevice>

    @Query("SELECT * FROM device")
    fun getDevices(): List<RoomDevice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDevices(devices: Collection<RoomDevice>)

    @Query("DELETE FROM device")
    fun deleteDevices()
}