package com.void.bikefitting.domain.usecase

import com.void.bikefitting.domain.utils.BikeFittingFormulas

/**
 * Use Case: Calculate Handlebar Height
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateHandlebarHeight {
    
    operator fun invoke(
        saddleHeight: Double,
        height: Double
    ): Double {
        return BikeFittingFormulas.calculateHandlebarHeight(saddleHeight, height)
    }
}
