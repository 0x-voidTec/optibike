package com.optibike.fitting.domain.repository

import com.optibike.fitting.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

/**
 * Measurement Repository Interface
 * Defines the contract for measurement data operations
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
interface MeasurementRepository {
    
    /**
     * Save a measurement to the database
     */
    suspend fun saveMeasurement(measurement: Measurement): Long
    
    /**
     * Get all measurements as a flow
     */
    fun getAllMeasurements(): Flow<List<Measurement>>
    
    /**
     * Get a specific measurement by ID
     */
    suspend fun getMeasurementById(id: Long): Measurement?
    
    /**
     * Get measurements for a specific user
     */
    fun getMeasurementsByUser(userId: String): Flow<List<Measurement>>

    /**
     * Get measurements for a specific bike
     */
    fun getMeasurementsByBike(bikeId: Long): Flow<List<Measurement>>
    
    /**
     * Update an existing measurement
     */
    suspend fun updateMeasurement(measurement: Measurement)
    
    /**
     * Delete a measurement by ID
     */
    suspend fun deleteMeasurement(id: Long)
    
    /**
     * Delete all measurements
     */
    suspend fun deleteAllMeasurements()
    
    /**
     * Get the latest measurement
     */
    suspend fun getLatestMeasurement(): Measurement?
}
