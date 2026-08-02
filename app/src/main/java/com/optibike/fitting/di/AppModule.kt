package com.optibike.fitting.di

import android.content.Context
import androidx.room.Room
import com.optibike.fitting.data.BikeFittingDatabase
import com.optibike.fitting.data.local.dao.BikeDao
import com.optibike.fitting.data.local.dao.MeasurementDao
import com.optibike.fitting.data.repository.BikeRepositoryImpl
import com.optibike.fitting.data.repository.MeasurementRepositoryImpl
import com.optibike.fitting.domain.repository.BikeRepository
import com.optibike.fitting.domain.repository.MeasurementRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * App Module for Hilt Dependency Injection
 * Provides database and repository instances
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideBikeFittingDatabase(
        @ApplicationContext context: Context
    ): BikeFittingDatabase {
        return BikeFittingDatabase.getDatabase(context)
    }
    
    @Provides
    @Singleton
    fun provideMeasurementDao(
        database: BikeFittingDatabase
    ): MeasurementDao {
        return database.measurementDao()
    }

    @Provides
    @Singleton
    fun provideBikeDao(
        database: BikeFittingDatabase
    ): BikeDao {
        return database.bikeDao()
    }
    
    @Provides
    @Singleton
    fun provideMeasurementRepository(
        measurementDao: MeasurementDao
    ): MeasurementRepository {
        return MeasurementRepositoryImpl(measurementDao)
    }

    @Provides
    @Singleton
    fun provideBikeRepository(
        bikeDao: BikeDao
    ): BikeRepository {
        return BikeRepositoryImpl(bikeDao)
    }
}
