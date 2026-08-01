package com.void.bikefitting.domain.utils

import com.void.bikefitting.domain.model.BikeType
import com.void.bikefitting.domain.model.Measurement
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for BikeFittingFormulas
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class BikeFittingFormulasTest {

    @Test
    fun `calculateSaddleHeight for ROAD returns correct value`() {
        // Given
        val height = 180.0
        val bikeType = BikeType.ROAD

        // When
        val result = BikeFittingFormulas.calculateSaddleHeight(height, bikeType)

        // Then
        val expected = 180.0 * 0.45 * 10 // 810mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `calculateSaddleHeight for GRAVEL returns correct value`() {
        // Given
        val height = 180.0
        val bikeType = BikeType.GRAVEL

        // When
        val result = BikeFittingFormulas.calculateSaddleHeight(height, bikeType)

        // Then
        val expected = 180.0 * 0.45 * 10 - 2 // 808mm (2mm lower for gravel)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `calculateSaddleHeight for different heights`() {
        // Test multiple heights
        val testCases = mapOf(
            150.0 to 675.0,  // 150 * 0.45 * 10
            160.0 to 720.0,  // 160 * 0.45 * 10
            170.0 to 765.0,  // 170 * 0.45 * 10
            190.0 to 855.0,  // 190 * 0.45 * 10
            200.0 to 900.0   // 200 * 0.45 * 10
        )

        testCases.forEach { (height, expected) ->
            val result = BikeFittingFormulas.calculateSaddleHeight(height, BikeType.ROAD)
            assertEquals(expected, result, 0.01)
        }
    }

    @Test
    fun `calculateSaddleTilt returns zero`() {
        // Given
        // No parameters needed

        // When
        val result = BikeFittingFormulas.calculateSaddleTilt()

        // Then
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `calculateSaddleForeAft for ROAD with height under 170`() {
        // Given
        val height = 165.0
        val inseam = 80.0
        val bikeType = BikeType.ROAD

        // When
        val result = BikeFittingFormulas.calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `calculateSaddleForeAft for ROAD with height 175`() {
        // Given
        val height = 175.0
        val inseam = 85.0
        val bikeType = BikeType.ROAD

        // When
        val result = BikeFittingFormulas.calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        assertEquals(5.0, result, 0.01)
    }

    @Test
    fun `calculateSaddleForeAft for ROAD with height over 180`() {
        // Given
        val height = 185.0
        val inseam = 90.0
        val bikeType = BikeType.ROAD

        // When
        val result = BikeFittingFormulas.calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        assertEquals(10.0, result, 0.01)
    }

    @Test
    fun `calculateSaddleForeAft for GRAVEL adjusts by minus 5`() {
        // Given
        val height = 175.0
        val inseam = 85.0
        val bikeType = BikeType.GRAVEL

        // When
        val result = BikeFittingFormulas.calculateSaddleForeAft(height, inseam, bikeType)

        // Then
        assertEquals(0.0, result, 0.01) // 5 - 5 = 0
    }

    @Test
    fun `calculateHandlebarHeight for height 180`() {
        // Given
        val saddleHeight = 810.0
        val height = 180.0

        // When
        val result = BikeFittingFormulas.calculateHandlebarHeight(saddleHeight, height)

        // Then
        // Height difference for 180cm is 81mm
        val expected = 810.0 - 81.0 // 729mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `calculateHandlebarHeight for different heights`() {
        // Test cases based on reference table
        val testCases = mapOf(
            150.0 to 50.0,
            160.0 to 60.0,
            170.0 to 70.0,
            180.0 to 81.0,
            190.0 to 96.0,
            200.0 to 111.0
        )

        testCases.forEach { (height, difference) ->
            val saddleHeight = height * 0.45 * 10
            val result = BikeFittingFormulas.calculateHandlebarHeight(saddleHeight, height)
            val expected = saddleHeight - difference
            assertEquals(expected, result, 0.01)
        }
    }

    @Test
    fun `calculateHandlebarWidth returns shoulder width`() {
        // Given
        val shoulderWidth = 45.0

        // When
        val result = BikeFittingFormulas.calculateHandlebarWidth(shoulderWidth)

        // Then
        assertEquals(shoulderWidth, result, 0.01)
    }

    @Test
    fun `calculateHandlebarWidth returns default when null`() {
        // Given
        val shoulderWidth = null

        // When
        val result = BikeFittingFormulas.calculateHandlebarWidth(shoulderWidth)

        // Then
        assertEquals(42.0, result, 0.01) // Default value
    }

    @Test
    fun `calculateCleatPosition with shoe size 42`() {
        // Given
        val shoeSize = 42
        val crankLength = 170

        // When
        val result = BikeFittingFormulas.calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 42.0 * 0.6 // 25.2
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `calculateCleatPosition with null values`() {
        // Given
        val shoeSize = null
        val crankLength = null

        // When
        val result = BikeFittingFormulas.calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 42.0 * 0.6 // Default shoe size
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `calculateAllParameters returns complete results`() {
        // Given
        val measurement = Measurement(
            height = 180.0,
            inseam = 85.0,
            shoulderWidth = 45.0,
            armLength = 60.0,
            torsoLength = 55.0,
            bikeType = BikeType.ROAD,
            shoeSize = 42,
            crankLength = 170
        )

        // When
        val results = BikeFittingFormulas.calculateAllParameters(measurement)

        // Then
        assertEquals(810.0, results.saddleHeight, 0.01)
        assertEquals(0.0, results.saddleTilt, 0.01)
        assertEquals(5.0, results.saddleForeAft, 0.01)
        assertEquals(729.0, results.handlebarHeight, 0.01) // 810 - 81
        assertEquals(115.0, results.saddleHandlebarDistance, 0.01) // 60 + 55
        assertEquals(45.0, results.handlebarWidth, 0.01)
        assertEquals(25.2, results.cleatPosition, 0.01)
    }

    @Test
    fun `calculateAllParameters for GRAVEL bike`() {
        // Given
        val measurement = Measurement(
            height = 180.0,
            inseam = 85.0,
            shoulderWidth = 45.0,
            bikeType = BikeType.GRAVEL
        )

        // When
        val results = BikeFittingFormulas.calculateAllParameters(measurement)

        // Then
        assertEquals(808.0, results.saddleHeight, 0.01) // 2mm lower for gravel
        assertEquals(0.0, results.saddleForeAft, 0.01) // 5 - 5 = 0 for gravel
    }
}
