package com.void.bikefitting.domain.usecase

import com.void.bikefitting.domain.utils.BikeFittingFormulas

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
