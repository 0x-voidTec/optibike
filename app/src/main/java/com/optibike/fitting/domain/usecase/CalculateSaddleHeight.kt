package com.optibike.fitting.domain.usecase

import com.optibike.fitting.domain.model.BikeType
import com.optibike.fitting.domain.utils.BikeFittingFormulas

/**
 * Use Case: Calculate Saddle Height
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateSaddleHeight {
    
    operator fun invoke(
        height: Double,
        bikeType: BikeType
    ): Double {
        return BikeFittingFormulas.calculateSaddleHeight(height, bikeType)
    }
}
