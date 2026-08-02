package com.optibike.fitting.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.optibike.fitting.data.local.dao.BikeDao
import com.optibike.fitting.data.local.dao.MeasurementDao
import com.optibike.fitting.data.local.entities.BikeEntity
import com.optibike.fitting.data.local.entities.MeasurementEntity

/**
 * Bike Fitting Database
 * Room database for storing measurement data
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Database(
    entities = [MeasurementEntity::class, BikeEntity::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(com.optibike.fitting.data.local.entities.MeasurementConverters::class)
abstract class BikeFittingDatabase : RoomDatabase() {
    
    abstract fun measurementDao(): MeasurementDao
    abstract fun bikeDao(): BikeDao
    
    companion object {
        @Volatile
        private var INSTANCE: BikeFittingDatabase? = null
        
        fun getDatabase(context: Context): BikeFittingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BikeFittingDatabase::class.java,
                    "bike_fitting_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
