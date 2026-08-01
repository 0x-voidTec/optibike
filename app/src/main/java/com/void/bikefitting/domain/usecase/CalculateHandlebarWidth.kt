package com.void.bikefitting.domain.usecase

import com.void.bikefitting.domain.utils.BikeFittingFormulas

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
