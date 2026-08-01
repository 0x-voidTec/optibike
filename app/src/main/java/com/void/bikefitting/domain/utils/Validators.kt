package com.void.bikefitting.domain.utils

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
        return height != null && height >= 100.0 && height <= 250.0
    }
    
    // Inseam validation (cm)
    fun validateInseam(inseam: Double?): Boolean {
        return inseam != null && inseam >= 50.0 && inseam <= 120.0
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
        bikeType: com.void.bikefitting.domain.model.BikeType?
    ): Boolean {
        return validateHeight(height) && 
               validateInseam(inseam) && 
               bikeType != null
    }
    
    // Get validation error message
    fun getValidationError(
        height: Double?,
        inseam: Double?,
        bikeType: com.void.bikefitting.domain.model.BikeType?
    ): String? {
        if (!validateHeight(height)) {
            return "Height must be between 100cm and 250cm"
        }
        if (!validateInseam(inseam)) {
            return "Inseam must be between 50cm and 120cm"
        }
        if (bikeType == null) {
            return "Please select bike type"
        }
        return null
    }
}
