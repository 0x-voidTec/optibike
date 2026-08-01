package com.void.bikefitting.domain.usecase

import com.void.bikefitting.domain.model.BikeType
import com.void.bikefitting.domain.utils.BikeFittingFormulas

/**
 * Use Case: Calculate Saddle Fore-Aft Position
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateSaddleForeAft {
    
    operator fun invoke(
        height: Double,
        inseam: Double,
        bikeType: BikeType
    ): Double {
        return BikeFittingFormulas.calculateSaddleForeAft(height, inseam, bikeType)
    }
}
