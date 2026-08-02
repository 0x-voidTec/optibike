package com.optibike.fitting.domain.usecase

import com.optibike.fitting.domain.model.BikeType
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for CalculateSaddleForeAft use case
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateSaddleForeAftTest {

    private val calculateSaddleForeAft = CalculateSaddleForeAft()

    @Test
    fun `invoke with ROAD bike and height under 170 returns zero`() {
        // Given
        val height = 165.0
        val inseam = 80.0
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `invoke with ROAD bike and height 175 returns plus 5`() {
        // Given
        val height = 175.0
        val inseam = 85.0
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        assertEquals(5.0, result, 0.01)
    }

    @Test
    fun `invoke with ROAD bike and height over 180 returns plus 10`() {
        // Given
        val height = 185.0
        val inseam = 90.0
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        assertEquals(10.0, result, 0.01)
    }

    @Test
    fun `invoke with GRAVEL bike and height 175 returns zero`() {
        // Given
        val height = 175.0
        val inseam = 85.0
        val bikeType = BikeType.GRAVEL

        // When
        val result = calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        // 5 (base) - 5 (gravel adjustment) = 0
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `invoke with GRAVEL bike and height 185 returns plus 5`() {
        // Given
        val height = 185.0
        val inseam = 90.0
        val bikeType = BikeType.GRAVEL

        // When
        val result = calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        // 10 (base) - 5 (gravel adjustment) = 5
        assertEquals(5.0, result, 0.01)
    }

    @Test
    fun `invoke with GRAVEL bike and height under 170 returns minus 5`() {
        // Given
        val height = 165.0
        val inseam = 80.0
        val bikeType = BikeType.GRAVEL

        // When
        val result = calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        // 0 (base) - 5 (gravel adjustment) = -5
        // But our implementation doesn't go below 0, so it should be 0
        // Actually, looking at the formula, it should be -5
        // Let's check the actual implementation
        assertEquals(-5.0, result, 0.01)
    }

    @Test
    fun `invoke with edge case height 170`() {
        // Given
        val height = 170.0
        val inseam = 85.0
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        // Height >= 170 && < 180 -> +5mm
        assertEquals(5.0, result, 0.01)
    }

    @Test
    fun `invoke with edge case height 180`() {
        // Given
        val height = 180.0
        val inseam = 90.0
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        // Height >= 180 -> +10mm
        assertEquals(10.0, result, 0.01)
    }
}
