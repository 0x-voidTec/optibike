package com.optibike.fitting.domain.usecase

import com.optibike.fitting.domain.utils.BikeFittingFormulas

/**
 * Use Case: Calculate Handlebar Width
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateHandlebarWidth {
    
    operator fun invoke(shoulderWidth: Double?): Double {
        return BikeFittingFormulas.calculateHandlebarWidth(shoulderWidth)
    }
}
