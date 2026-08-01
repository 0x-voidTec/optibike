package com.void.bikefitting.domain.utils

import com.void.bikefitting.domain.model.BikeType
import com.void.bikefitting.domain.model.Measurement
import com.void.bikefitting.domain.model.MeasurementResults

/**
 * Bike Fitting Formulas
 * Contains all calculation formulas based on industry standards
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
object BikeFittingFormulas {
    
    /**
     * Calculate optimal saddle height
     * Formula: height * 0.45 (for Road)
     * For Gravel: height * 0.45 - 2mm
     */
    fun calculateSaddleHeight(height: Double, bikeType: BikeType): Double {
        val baseHeight = height * 0.45 * 10 // Convert cm to mm
        return when (bikeType) {
            BikeType.ROAD -> baseHeight
            BikeType.GRAVEL -> baseHeight - 2
        }
    }
    
    /**
     * Calculate saddle tilt
     * Default: 0° (horizontal)
     * Can be adjusted based on riding style
     */
    fun calculateSaddleTilt(): Double {
        return 0.0 // Default horizontal position
    }
    
    /**
     * Calculate saddle fore-aft position
     * Based on frame reach and riding style
     */
    fun calculateSaddleForeAft(
        height: Double,
        inseam: Double,
        bikeType: BikeType,
        frameReach: Double? = null
    ): Double {
        // Simplified calculation based on height
        val basePosition = when {
            height < 170 -> 0.0 // Neutral position for shorter riders
            height >= 170 && height < 180 -> 5.0 // +5mm for medium height
            else -> 10.0 // +10mm for taller riders
        }
        
        // Gravel adjustment
        return when (bikeType) {
            BikeType.ROAD -> basePosition
            BikeType.GRAVEL -> basePosition - 5 // -5mm for gravel bikes
        }
    }
    
    /**
     * Calculate handlebar height
     * Formula: A - B = C (saddle height - handlebar height = difference)
     * Difference based on height from reference table
     */
    fun calculateHandlebarHeight(
        saddleHeight: Double,
        height: Double
    ): Double {
        // Get height difference from reference table
        val heightDifference = getHandlebarHeightDifference(height)
        // Handlebar height = Saddle height - difference
        return saddleHeight - heightDifference
    }
    
    /**
     * Get handlebar height difference from reference table
     * Based on Table 2 from requirements
     */
    private fun getHandlebarHeightDifference(height: Double): Double {
        return when {
            height <= 150 -> 50.0
            height <= 160 -> 60.0
            height <= 170 -> 70.0
            height <= 180 -> 81.0
            height <= 190 -> 96.0
            else -> 111.0
        }
    }
    
    /**
     * Get gravel handlebar height difference from reference table
     */
    private fun getGravelHandlebarHeightDifference(height: Double): Double {
        return when {
            height <= 150 -> 20.0
            height <= 160 -> 30.0
            height <= 170 -> 40.0
            height <= 180 -> 51.0
            height <= 190 -> 66.0
            else -> 81.0
        }
    }
    
    /**
     * Calculate saddle-handlebar distance
     * Based on frame reach and rider measurements
     */
    fun calculateSaddleHandlebarDistance(
        armLength: Double?,
        torsoLength: Double?,
        frameReach: Double? = null
    ): Double {
        // Simplified calculation
        val baseDistance = armLength?.plus(torsoLength ?: 0.0) ?: 0.0
        
        // Adjust based on frame reach if available
        return when {
            frameReach != null && frameReach < 390 -> baseDistance - 5
            frameReach != null && frameReach >= 410 -> baseDistance + 5
            else -> baseDistance
        }
    }
    
    /**
     * Calculate handlebar width
     * Based on shoulder width from reference table
     */
    fun calculateHandlebarWidth(shoulderWidth: Double?): Double {
        return shoulderWidth ?: 42.0 // Default 42cm if not provided
    }
    
    /**
     * Calculate cleat position
     * Based on shoe size and crank length
     */
    fun calculateCleatPosition(
        shoeSize: Int?,
        crankLength: Int?
    ): Double {
        // Simplified calculation
        // Default: shoe size * 0.6 (approximate)
        return (shoeSize?.toDouble() ?: 42.0) * 0.6
    }
    
    /**
     * Calculate all parameters for a measurement
     */
    fun calculateAllParameters(measurement: Measurement): MeasurementResults {
        val height = measurement.height ?: 170.0 // Default height if not provided
        val inseam = measurement.inseam ?: 80.0 // Default inseam
        val shoulderWidth = measurement.shoulderWidth
        val armLength = measurement.armLength
        val torsoLength = measurement.torsoLength
        val bikeType = measurement.bikeType
        val shoeSize = measurement.shoeSize
        val crankLength = measurement.crankLength
        
        // Calculate saddle height
        val saddleHeight = calculateSaddleHeight(height, bikeType)
        
        // Calculate saddle tilt
        val saddleTilt = calculateSaddleTilt()
        
        // Calculate saddle fore-aft
        val saddleForeAft = calculateSaddleForeAft(
            height = height,
            inseam = inseam,
            bikeType = bikeType
        )
        
        // Calculate handlebar height
        val handlebarHeight = calculateHandlebarHeight(
            saddleHeight = saddleHeight,
            height = height
        )
        
        // Calculate saddle-handlebar distance
        val saddleHandlebarDistance = calculateSaddleHandlebarDistance(
            armLength = armLength,
            torsoLength = torsoLength
        )
        
        // Calculate handlebar width
        val handlebarWidth = calculateHandlebarWidth(shoulderWidth)
        
        // Calculate cleat position
        val cleatPosition = calculateCleatPosition(shoeSize, crankLength)
        
        // Generate recommendations
        val recommendations = generateRecommendations(
            measurement = measurement,
            results = MeasurementResults(
                saddleHeight = saddleHeight,
                saddleTilt = saddleTilt,
                saddleForeAft = saddleForeAft,
                handlebarHeight = handlebarHeight,
                saddleHandlebarDistance = saddleHandlebarDistance,
                handlebarWidth = handlebarWidth,
                cleatPosition = cleatPosition
            )
        )
        
        return MeasurementResults(
            saddleHeight = saddleHeight,
            saddleTilt = saddleTilt,
            saddleForeAft = saddleForeAft,
            handlebarHeight = handlebarHeight,
            saddleHandlebarDistance = saddleHandlebarDistance,
            handlebarWidth = handlebarWidth,
            cleatPosition = cleatPosition,
            recommendations = recommendations
        )
    }
    
    /**
     * Generate recommendations based on current vs calculated values
     */
    private fun generateRecommendations(
        measurement: Measurement,
        results: MeasurementResults
    ): List<String> {
        val recommendations = mutableListOf<String>()
        
        // Saddle height recommendation
        measurement.currentSaddleHeight?.let { current ->
            val difference = results.saddleHeight - current
            if (difference > 5) {
                recommendations.add("Increase saddle height by ${difference.toInt()}mm")
            } else if (difference < -5) {
                recommendations.add("Decrease saddle height by ${Math.abs(difference.toInt())}mm")
            }
        }
        
        // Handlebar height recommendation
        measurement.currentHandlebarHeight?.let { current ->
            val difference = results.handlebarHeight - current
            if (difference > 5) {
                recommendations.add("Raise handlebar by ${difference.toInt()}mm")
            } else if (difference < -5) {
                recommendations.add("Lower handlebar by ${Math.abs(difference.toInt())}mm")
            }
        }
        
        // General recommendations
        if (measurement.bikeType == BikeType.GRAVEL) {
            recommendations.add("For gravel bikes, consider a slightly lower saddle height for better control")
        }
        
        return recommendations
    }
}
