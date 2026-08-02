package com.optibike.fitting.domain.usecase

import com.optibike.fitting.domain.utils.BikeFittingFormulas

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
