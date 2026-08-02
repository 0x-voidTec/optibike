package com.optibike.fitting.data.repository

import com.optibike.fitting.data.local.dao.MeasurementDao
import com.optibike.fitting.data.local.entities.MeasurementEntity
import com.optibike.fitting.domain.model.Measurement
import com.optibike.fitting.domain.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Measurement Repository Implementation
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class MeasurementRepositoryImpl @Inject constructor(
    private val measurementDao: MeasurementDao
) : MeasurementRepository {
    
    override suspend fun saveMeasurement(measurement: Measurement): Long {
        val entity = measurement.toEntity()
        return measurementDao.insertMeasurement(entity)
    }
    
    override fun getAllMeasurements(): Flow<List<Measurement>> {
        return measurementDao.getAllMeasurements().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getMeasurementById(id: Long): Measurement? {
        return measurementDao.getMeasurementById(id)?.toDomain()
    }
    
    override fun getMeasurementsByUser(userId: String): Flow<List<Measurement>> {
        return measurementDao.getMeasurementsByUser(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getMeasurementsByBike(bikeId: Long): Flow<List<Measurement>> {
        return measurementDao.getMeasurementsByBike(bikeId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun updateMeasurement(measurement: Measurement) {
        measurementDao.updateMeasurement(measurement.toEntity())
    }
    
    override suspend fun deleteMeasurement(id: Long) {
        measurementDao.deleteMeasurementById(id)
    }
    
    override suspend fun deleteAllMeasurements() {
        measurementDao.deleteAllMeasurements()
    }
    
    override suspend fun getLatestMeasurement(): Measurement? {
        return measurementDao.getLatestMeasurement()?.toDomain()
    }
}

/**
 * Extension functions for converting between Domain and Entity models
 */
fun Measurement.toEntity(): MeasurementEntity {
    return MeasurementEntity(
        id = id,
        userId = userId,
        bikeId = bikeId,
        bikeType = bikeType,
        height = height,
        inseam = inseam,
        shoulderWidth = shoulderWidth,
        armLength = armLength,
        torsoLength = torsoLength,
        frameSize = frameSize,
        currentSaddleHeight = currentSaddleHeight,
        currentHandlebarHeight = currentHandlebarHeight,
        shoeSize = shoeSize,
        crankLength = crankLength,
        calculatedSaddleHeight = calculatedSaddleHeight,
        calculatedSaddleTilt = calculatedSaddleTilt,
        calculatedSaddleForeAft = calculatedSaddleForeAft,
        calculatedHandlebarHeight = calculatedHandlebarHeight,
        calculatedSaddleHandlebarDistance = calculatedSaddleHandlebarDistance,
        calculatedHandlebarWidth = calculatedHandlebarWidth,
        calculatedCleatPosition = calculatedCleatPosition,
        timestamp = timestamp,
        notes = notes,
        isComplete = isComplete
    )
}

fun MeasurementEntity.toDomain(): Measurement {
    return Measurement(
        id = id,
        userId = userId,
        bikeId = bikeId,
        bikeType = bikeType,
        height = height,
        inseam = inseam,
        shoulderWidth = shoulderWidth,
        armLength = armLength,
        torsoLength = torsoLength,
        frameSize = frameSize,
        currentSaddleHeight = currentSaddleHeight,
        currentHandlebarHeight = currentHandlebarHeight,
        shoeSize = shoeSize,
        crankLength = crankLength,
        calculatedSaddleHeight = calculatedSaddleHeight,
        calculatedSaddleTilt = calculatedSaddleTilt,
        calculatedSaddleForeAft = calculatedSaddleForeAft,
        calculatedHandlebarHeight = calculatedHandlebarHeight,
        calculatedSaddleHandlebarDistance = calculatedSaddleHandlebarDistance,
        calculatedHandlebarWidth = calculatedHandlebarWidth,
        calculatedCleatPosition = calculatedCleatPosition,
        timestamp = timestamp,
        notes = notes,
        isComplete = isComplete
    )
}
