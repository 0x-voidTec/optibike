package com.optibike.fitting.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.optibike.fitting.data.local.entities.MeasurementEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Measurements
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Dao
interface MeasurementDao {
    
    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(measurement: MeasurementEntity): Long
    
    // Read
    @Query("SELECT * FROM measurements ORDER BY timestamp DESC")
    fun getAllMeasurements(): Flow<List<MeasurementEntity>>
    
    @Query("SELECT * FROM measurements WHERE id = :id")
    suspend fun getMeasurementById(id: Long): MeasurementEntity?
    
    @Query("SELECT * FROM measurements WHERE userId = :userId ORDER BY timestamp DESC")
    fun getMeasurementsByUser(userId: String): Flow<List<MeasurementEntity>>
    
    @Query("SELECT * FROM measurements WHERE bikeType = :bikeType ORDER BY timestamp DESC")
    fun getMeasurementsByBikeType(bikeType: String): Flow<List<MeasurementEntity>>
    
    @Query("SELECT * FROM measurements WHERE isComplete = 1 ORDER BY timestamp DESC")
    fun getCompletedMeasurements(): Flow<List<MeasurementEntity>>
    
    // Update
    @Update
    suspend fun updateMeasurement(measurement: MeasurementEntity)
    
    // Delete
    @Delete
    suspend fun deleteMeasurement(measurement: MeasurementEntity)
    
    @Query("DELETE FROM measurements WHERE id = :id")
    suspend fun deleteMeasurementById(id: Long)
    
    @Query("DELETE FROM measurements")
    suspend fun deleteAllMeasurements()
    
    // Count
    @Query("SELECT COUNT(*) FROM measurements")
    suspend fun getMeasurementCount(): Int
    
    // Latest
    @Query("SELECT * FROM measurements ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestMeasurement(): MeasurementEntity?
}
