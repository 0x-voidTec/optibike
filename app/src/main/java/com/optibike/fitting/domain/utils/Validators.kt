package com.optibike.fitting.domain.utils

/**
 * Input Validators
 * Validates user input for bike fitting measurements
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
object Validators {
    
    // Height validation (cm)
    fun validateHeight(height: Double?): Boolean {
        return height != null && height >= 50.0 && height <= 250.0
    }
    
    // Inseam validation (cm)
    fun validateInseam(inseam: Double?): Boolean {
        return inseam != null && inseam >= 30.0 && inseam <= 150.0
    }
    
    // Shoulder width validation (cm)
    fun validateShoulderWidth(shoulderWidth: Double?): Boolean {
        return shoulderWidth != null && shoulderWidth >= 30.0 && shoulderWidth <= 60.0
    }
    
    // Arm length validation (cm)
    fun validateArmLength(armLength: Double?): Boolean {
        return armLength != null && armLength >= 40.0 && armLength <= 100.0
    }
    
    // Torso length validation (cm)
    fun validateTorsoLength(torsoLength: Double?): Boolean {
        return torsoLength != null && torsoLength >= 40.0 && torsoLength <= 100.0
    }
    
    // Frame size validation (cm)
    fun validateFrameSize(frameSize: Double?): Boolean {
        return frameSize != null && frameSize >= 40.0 && frameSize <= 70.0
    }
    
    // Saddle height validation (mm)
    fun validateSaddleHeight(saddleHeight: Double?): Boolean {
        return saddleHeight != null && saddleHeight >= 500.0 && saddleHeight <= 900.0
    }
    
    // Handlebar height validation (mm)
    fun validateHandlebarHeight(handlebarHeight: Double?): Boolean {
        return handlebarHeight != null && handlebarHeight >= 400.0 && handlebarHeight <= 800.0
    }
    
    // Shoe size validation (EU)
    fun validateShoeSize(shoeSize: Int?): Boolean {
        return shoeSize != null && shoeSize >= 30 && shoeSize <= 50
    }
    
    // Crank length validation (mm)
    fun validateCrankLength(crankLength: Int?): Boolean {
        return crankLength != null && crankLength >= 140 && crankLength <= 190
    }
    
    // Validate all required fields for basic calculation
    fun validateRequiredMeasurements(
        height: Double?,
        inseam: Double?,
        bikeType: com.optibike.fitting.domain.model.BikeType?
    ): Boolean {
        return validateHeight(height) && 
               validateInseam(inseam) && 
               bikeType != null
    }
    
    // Get validation error message
    fun getValidationError(
        height: Double?,
        inseam: Double?,
        bikeType: com.optibike.fitting.domain.model.BikeType?
    ): String? {
        if (!validateHeight(height)) {
            return "ERR_HEIGHT_RANGE"
        }
        if (!validateInseam(inseam)) {
            return "ERR_INSEAM_RANGE"
        }
        if (bikeType == null) {
            return "ERR_BIKE_TYPE_REQUIRED"
        }
        return null
    }
}
