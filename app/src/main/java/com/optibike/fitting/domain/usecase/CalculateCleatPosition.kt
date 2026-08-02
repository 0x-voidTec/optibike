package com.optibike.fitting.domain.usecase

import com.optibike.fitting.domain.utils.BikeFittingFormulas

/**
 * Use Case: Calculate Cleat Position
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateCleatPosition {
    
    operator fun invoke(
        shoeSize: Int?,
        crankLength: Int?
    ): Double {
        return BikeFittingFormulas.calculateCleatPosition(shoeSize, crankLength)
    }
}
