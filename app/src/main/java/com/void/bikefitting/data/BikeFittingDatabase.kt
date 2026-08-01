package com.void.bikefitting.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.void.bikefitting.data.local.dao.MeasurementDao
import com.void.bikefitting.data.local.entities.MeasurementEntity

/**
 * Bike Fitting Database
 * Room database for storing measurement data
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Database(
    entities = [MeasurementEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(com.void.bikefitting.data.local.entities.MeasurementConverters::class)
abstract class BikeFittingDatabase : RoomDatabase() {
    
    abstract fun measurementDao(): MeasurementDao
    
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
