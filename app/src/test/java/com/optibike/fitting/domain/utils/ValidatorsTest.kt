package com.optibike.fitting.domain.utils

import com.optibike.fitting.domain.model.BikeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for Validators
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class ValidatorsTest {

    @Test
    fun `validateHeight returns true for valid height`() {
        // Given
        val height = 175.0

        // When
        val result = Validators.validateHeight(height)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateHeight returns false for height below minimum`() {
        // Given
        val height = 99.0

        // When
        val result = Validators.validateHeight(height)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateHeight returns false for height above maximum`() {
        // Given
        val height = 251.0

        // When
        val result = Validators.validateHeight(height)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateHeight returns false for null`() {
        // Given
        val height = null

        // When
        val result = Validators.validateHeight(height)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateInseam returns true for valid inseam`() {
        // Given
        val inseam = 80.0

        // When
        val result = Validators.validateInseam(inseam)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateInseam returns false for inseam below minimum`() {
        // Given
        val inseam = 49.0

        // When
        val result = Validators.validateInseam(inseam)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateInseam returns false for inseam above maximum`() {
        // Given
        val inseam = 121.0

        // When
        val result = Validators.validateInseam(inseam)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateShoulderWidth returns true for valid width`() {
        // Given
        val shoulderWidth = 45.0

        // When
        val result = Validators.validateShoulderWidth(shoulderWidth)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateShoulderWidth returns false for width below minimum`() {
        // Given
        val shoulderWidth = 29.0

        // When
        val result = Validators.validateShoulderWidth(shoulderWidth)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateShoulderWidth returns false for width above maximum`() {
        // Given
        val shoulderWidth = 61.0

        // When
        val result = Validators.validateShoulderWidth(shoulderWidth)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateArmLength returns true for valid length`() {
        // Given
        val armLength = 60.0

        // When
        val result = Validators.validateArmLength(armLength)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateArmLength returns false for length below minimum`() {
        // Given
        val armLength = 39.0

        // When
        val result = Validators.validateArmLength(armLength)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateArmLength returns false for length above maximum`() {
        // Given
        val armLength = 101.0

        // When
        val result = Validators.validateArmLength(armLength)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateTorsoLength returns true for valid length`() {
        // Given
        val torsoLength = 55.0

        // When
        val result = Validators.validateTorsoLength(torsoLength)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateFrameSize returns true for valid size`() {
        // Given
        val frameSize = 54.0

        // When
        val result = Validators.validateFrameSize(frameSize)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateSaddleHeight returns true for valid height`() {
        // Given
        val saddleHeight = 700.0

        // When
        val result = Validators.validateSaddleHeight(saddleHeight)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateHandlebarHeight returns true for valid height`() {
        // Given
        val handlebarHeight = 600.0

        // When
        val result = Validators.validateHandlebarHeight(handlebarHeight)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateShoeSize returns true for valid size`() {
        // Given
        val shoeSize = 42

        // When
        val result = Validators.validateShoeSize(shoeSize)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateShoeSize returns false for size below minimum`() {
        // Given
        val shoeSize = 29

        // When
        val result = Validators.validateShoeSize(shoeSize)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateShoeSize returns false for size above maximum`() {
        // Given
        val shoeSize = 51

        // When
        val result = Validators.validateShoeSize(shoeSize)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateCrankLength returns true for valid length`() {
        // Given
        val crankLength = 170

        // When
        val result = Validators.validateCrankLength(crankLength)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateCrankLength returns false for length below minimum`() {
        // Given
        val crankLength = 139

        // When
        val result = Validators.validateCrankLength(crankLength)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateCrankLength returns false for length above maximum`() {
        // Given
        val crankLength = 191

        // When
        val result = Validators.validateCrankLength(crankLength)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateRequiredMeasurements returns true for valid data`() {
        // Given
        val height = 180.0
        val inseam = 85.0
        val bikeType = BikeType.ROAD

        // When
        val result = Validators.validateRequiredMeasurements(height, inseam, bikeType)

        // Then
        assertTrue(result)
    }

    @Test
    fun `validateRequiredMeasurements returns false when height is invalid`() {
        // Given
        val height = 99.0
        val inseam = 85.0
        val bikeType = BikeType.ROAD

        // When
        val result = Validators.validateRequiredMeasurements(height, inseam, bikeType)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateRequiredMeasurements returns false when inseam is invalid`() {
        // Given
        val height = 180.0
        val inseam = 49.0
        val bikeType = BikeType.ROAD

        // When
        val result = Validators.validateRequiredMeasurements(height, inseam, bikeType)

        // Then
        assertFalse(result)
    }

    @Test
    fun `validateRequiredMeasurements returns false when bikeType is null`() {
        // Given
        val height = 180.0
        val inseam = 85.0
        val bikeType = null

        // When
        val result = Validators.validateRequiredMeasurements(height, inseam, bikeType)

        // Then
        assertFalse(result)
    }

    @Test
    fun `getValidationError returns null for valid data`() {
        // Given
        val height = 180.0
        val inseam = 85.0
        val bikeType = BikeType.ROAD

        // When
        val result = Validators.getValidationError(height, inseam, bikeType)

        // Then
        assertEquals(null, result)
    }

    @Test
    fun `getValidationError returns message for invalid height`() {
        // Given
        val height = 99.0
        val inseam = 85.0
        val bikeType = BikeType.ROAD

        // When
        val result = Validators.getValidationError(height, inseam, bikeType)

        // Then
        assertEquals("Height must be between 100cm and 250cm", result)
    }

    @Test
    fun `getValidationError returns message for invalid inseam`() {
        // Given
        val height = 180.0
        val inseam = 49.0
        val bikeType = BikeType.ROAD

        // When
        val result = Validators.getValidationError(height, inseam, bikeType)

        // Then
        assertEquals("Inseam must be between 50cm and 120cm", result)
    }

    @Test
    fun `getValidationError returns message for null bikeType`() {
        // Given
        val height = 180.0
        val inseam = 85.0
        val bikeType = null

        // When
        val result = Validators.getValidationError(height, inseam, bikeType)

        // Then
        assertEquals("Please select bike type", result)
    }
}
