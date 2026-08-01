package com.void.bikefitting.domain.usecase

import com.void.bikefitting.domain.utils.BikeFittingFormulas

/**
 * Use Case: Calculate Saddle Tilt
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateSaddleTilt {
    
    operator fun invoke(): Double {
        return BikeFittingFormulas.calculateSaddleTilt()
    }
}
