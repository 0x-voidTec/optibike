package com.void.bikefitting.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for CalculateHandlebarHeight use case
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateHandlebarHeightTest {

    private val calculateHandlebarHeight = CalculateHandlebarHeight()

    @Test
    fun `invoke with height 180 returns correct value`() {
        // Given
        val saddleHeight = 810.0
        val height = 180.0

        // When
        val result = calculateHandlebarHeight(saddleHeight, height)

        // Then
        // Height difference for 180cm is 81mm
        val expected = 810.0 - 81.0 // 729mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with height 150 returns correct value`() {
        // Given
        val saddleHeight = 675.0
        val height = 150.0

        // When
        val result = calculateHandlebarHeight(saddleHeight, height)

        // Then
        // Height difference for 150cm is 50mm
        val expected = 675.0 - 50.0 // 625mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with height 160 returns correct value`() {
        // Given
        val saddleHeight = 720.0
        val height = 160.0

        // When
        val result = calculateHandlebarHeight(saddleHeight, height)

        // Then
        // Height difference for 160cm is 60mm
        val expected = 720.0 - 60.0 // 660mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with height 170 returns correct value`() {
        // Given
        val saddleHeight = 765.0
        val height = 170.0

        // When
        val result = calculateHandlebarHeight(saddleHeight, height)

        // Then
        // Height difference for 170cm is 70mm
        val expected = 765.0 - 70.0 // 695mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with height 190 returns correct value`() {
        // Given
        val saddleHeight = 855.0
        val height = 190.0

        // When
        val result = calculateHandlebarHeight(saddleHeight, height)

        // Then
        // Height difference for 190cm is 96mm
        val expected = 855.0 - 96.0 // 759mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with height 200 returns correct value`() {
        // Given
        val saddleHeight = 900.0
        val height = 200.0

        // When
        val result = calculateHandlebarHeight(saddleHeight, height)

        // Then
        // Height difference for 200cm is 111mm
        val expected = 900.0 - 111.0 // 789mm
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with edge case height 100`() {
        // Given
        val saddleHeight = 450.0
        val height = 100.0

        // When
        val result = calculateHandlebarHeight(saddleHeight, height)

        // Then
        // Height difference for 100cm is 50mm (same as 150cm)
        val expected = 450.0 - 50.0 // 400mm
        assertEquals(expected, result, 0.01)
    }
}
