package com.optibike.fitting.domain.usecase

import com.optibike.fitting.domain.model.BikeType
import com.optibike.fitting.domain.utils.BikeFittingFormulas

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
