package com.void.bikefitting.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.void.bikefitting.domain.model.BikeType
import java.time.LocalDateTime

/**
 * Measurement Entity for Room Database
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Entity(tableName = "measurements")
@TypeConverters(MeasurementConverters::class)
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String = "default",
    val bikeType: BikeType = BikeType.ROAD,
    val height: Double? = null,
    val inseam: Double? = null,
    val shoulderWidth: Double? = null,
    val armLength: Double? = null,
    val torsoLength: Double? = null,
    val frameSize: Double? = null,
    val currentSaddleHeight: Double? = null,
    val currentHandlebarHeight: Double? = null,
    val shoeSize: Int? = null,
    val crankLength: Int? = null,
    val calculatedSaddleHeight: Double? = null,
    val calculatedSaddleTilt: Double? = null,
    val calculatedSaddleForeAft: Double? = null,
    val calculatedHandlebarHeight: Double? = null,
    val calculatedSaddleHandlebarDistance: Double? = null,
    val calculatedHandlebarWidth: Double? = null,
    val calculatedCleatPosition: Double? = null,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val notes: String = "",
    val isComplete: Boolean = false
)

/**
 * Type Converters for Room Database
 */
class MeasurementConverters {
    @androidx.room.TypeConverter
    fun fromBikeType(value: String?): BikeType? {
        return value?.let { BikeType.valueOf(it) }
    }
    
    @androidx.room.TypeConverter
    fun bikeTypeToString(bikeType: BikeType?): String? {
        return bikeType?.name
    }
    
    @androidx.room.TypeConverter
    fun fromLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }
    
    @androidx.room.TypeConverter
    fun localDateTimeToString(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }
}
