package com.optibike.fitting.data.repository

import com.optibike.fitting.data.local.dao.BikeDao
import com.optibike.fitting.data.local.entities.toDomain
import com.optibike.fitting.data.local.entities.toEntity
import com.optibike.fitting.domain.model.Bike
import com.optibike.fitting.domain.repository.BikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of BikeRepository
 */
class BikeRepositoryImpl @Inject constructor(
    private val bikeDao: BikeDao
) : BikeRepository {
    override suspend fun insertBike(bike: Bike): Long {
        return bikeDao.insertBike(bike.toEntity())
    }

    override fun getAllBikes(): Flow<List<Bike>> {
        return bikeDao.getAllBikes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getBikeById(id: Long): Bike? {
        return bikeDao.getBikeById(id)?.toDomain()
    }

    override suspend fun updateBike(bike: Bike) {
        bikeDao.updateBike(bike.toEntity())
    }

    override suspend fun deleteBike(bike: Bike) {
        bikeDao.deleteBike(bike.toEntity())
    }

    override suspend fun deleteBikeById(id: Long) {
        bikeDao.deleteBikeById(id)
    }

    override suspend fun getBikeCount(): Int {
        return bikeDao.getBikeCount()
    }
}
