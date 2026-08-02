package com.optibike.fitting.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.optibike.fitting.data.local.entities.BikeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Bikes
 */
@Dao
interface BikeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBike(bike: BikeEntity): Long

    @Query("SELECT * FROM bikes ORDER BY createdAt DESC")
    fun getAllBikes(): Flow<List<BikeEntity>>

    @Query("SELECT * FROM bikes WHERE id = :id")
    suspend fun getBikeById(id: Long): BikeEntity?

    @Update
    suspend fun updateBike(bike: BikeEntity)

    @Delete
    suspend fun deleteBike(bike: BikeEntity)

    @Query("DELETE FROM bikes WHERE id = :id")
    suspend fun deleteBikeById(id: Long)

    @Query("SELECT COUNT(*) FROM bikes")
    suspend fun getBikeCount(): Int
}
