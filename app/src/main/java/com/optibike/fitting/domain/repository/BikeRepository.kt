package com.optibike.fitting.domain.repository

import com.optibike.fitting.domain.model.Bike
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Bike Profiles
 */
interface BikeRepository {
    suspend fun insertBike(bike: Bike): Long
    fun getAllBikes(): Flow<List<Bike>>
    suspend fun getBikeById(id: Long): Bike?
    suspend fun updateBike(bike: Bike)
    suspend fun deleteBike(bike: Bike)
    suspend fun deleteBikeById(id: Long)
    suspend fun getBikeCount(): Int
}
