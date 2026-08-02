package com.optibike.fitting.domain.usecase

import com.optibike.fitting.domain.utils.BikeFittingFormulas

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
