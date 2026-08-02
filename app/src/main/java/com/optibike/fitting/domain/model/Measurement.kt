package com.optibike.fitting.domain.model

import java.time.LocalDateTime

/**
 * Measurement Data Model
 * Represents a complete bike fitting measurement session
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
data class Measurement(
    val id: Long = 0,
    val userId: String = "default", // For future multi-user support
    val bikeType: BikeType = BikeType.ROAD,
    val height: Double? = null, // cm
    val inseam: Double? = null, // cm
    val shoulderWidth: Double? = null, // cm
    val armLength: Double? = null, // cm
    val torsoLength: Double? = null, // cm
    val frameSize: Double? = null, // cm
    val currentSaddleHeight: Double? = null, // mm
    val currentHandlebarHeight: Double? = null, // mm
    val shoeSize: Int? = null, // EU size
    val crankLength: Int? = null, // mm
    
    // Calculated values
    val calculatedSaddleHeight: Double? = null, // mm
    val calculatedSaddleTilt: Double? = null, // degrees
    val calculatedSaddleForeAft: Double? = null, // mm
    val calculatedHandlebarHeight: Double? = null, // mm
    val calculatedSaddleHandlebarDistance: Double? = null, // mm
    val calculatedHandlebarWidth: Double? = null, // mm
    val calculatedCleatPosition: Double? = null, // mm
    
    // Metadata
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val notes: String = "",
    val isComplete: Boolean = false
) {
    // Check if all required measurements are present
    fun hasRequiredMeasurements(): Boolean {
        return height != null && inseam != null
    }
    
    // Check if calculation can be performed
    fun canCalculate(): Boolean {
        return hasRequiredMeasurements() && bikeType != null
    }
}

/**
 * Measurement Input Data
 * Used for form input before calculation
 */
data class MeasurementInput(
    val height: Double? = null,
    val inseam: Double? = null,
    val shoulderWidth: Double? = null,
    val armLength: Double? = null,
    val torsoLength: Double? = null,
    val bikeType: BikeType = BikeType.ROAD,
    val frameSize: Double? = null,
    val currentSaddleHeight: Double? = null,
    val currentHandlebarHeight: Double? = null,
    val shoeSize: Int? = null,
    val crankLength: Int? = null
) {
    fun toMeasurement(): Measurement {
        return Measurement(
            height = height,
            inseam = inseam,
            shoulderWidth = shoulderWidth,
            armLength = armLength,
            torsoLength = torsoLength,
            bikeType = bikeType,
            frameSize = frameSize,
            currentSaddleHeight = currentSaddleHeight,
            currentHandlebarHeight = currentHandlebarHeight,
            shoeSize = shoeSize,
            crankLength = crankLength
        )
    }
}

/**
 * Measurement Results
 * Contains calculated bike fitting parameters
 */
data class MeasurementResults(
    val saddleHeight: Double, // mm
    val saddleTilt: Double, // degrees
    val saddleForeAft: Double, // mm
    val handlebarHeight: Double, // mm
    val saddleHandlebarDistance: Double, // mm
    val handlebarWidth: Double, // mm
    val cleatPosition: Double, // mm
    val recommendations: List<String> = emptyList()
)
