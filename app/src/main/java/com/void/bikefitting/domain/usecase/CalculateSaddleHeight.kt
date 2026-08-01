package com.void.bikefitting.domain.usecase

import com.void.bikefitting.domain.model.BikeType
import com.void.bikefitting.domain.utils.BikeFittingFormulas

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
