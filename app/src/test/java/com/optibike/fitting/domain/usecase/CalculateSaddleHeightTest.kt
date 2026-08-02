package com.optibike.fitting.domain.usecase

import com.optibike.fitting.domain.model.BikeType
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for CalculateSaddleHeight use case
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateSaddleHeightTest {

    private val calculateSaddleHeight = CalculateSaddleHeight()

    @Test
    fun `invoke with ROAD bike type returns correct value`() {
        // Given
        val height = 180.0
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleHeight(height, bikeType)

        // Then
        val expected = 180.0 * 0.45 * 10 // 810mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with GRAVEL bike type returns correct value`() {
        // Given
        val height = 180.0
        val bikeType = BikeType.GRAVEL

        // When
        val result = calculateSaddleHeight(height, bikeType)

        // Then
        val expected = 180.0 * 0.45 * 10 - 2 // 808mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with different heights for ROAD`() {
        // Test multiple heights
        val testCases = mapOf(
            150.0 to 675.0,
            160.0 to 720.0,
            170.0 to 765.0,
            190.0 to 855.0,
            200.0 to 900.0
        )

        testCases.forEach { (height, expected) ->
            val result = calculateSaddleHeight(height, BikeType.ROAD)
            assertEquals(expected, result, 0.01)
        }
    }

    @Test
    fun `invoke with edge case height 100`() {
        // Given
        val height = 100.0
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleHeight(height, bikeType)

        // Then
        val expected = 100.0 * 0.45 * 10 // 450mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with edge case height 250`() {
        // Given
        val height = 250.0
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleHeight(height, bikeType)

        // Then
        val expected = 250.0 * 0.45 * 10 // 1125mm
        assertEquals(expected, result, 0.01)
    }
}
