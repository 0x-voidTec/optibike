package com.optibike.fitting.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for CalculateCleatPosition use case
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateCleatPositionTest {

    private val calculateCleatPosition = CalculateCleatPosition()

    @Test
    fun `invoke with shoe size 42 returns correct value`() {
        // Given
        val shoeSize = 42
        val crankLength = 170

        // When
        val result = calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 42.0 * 0.6 // 25.2
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with shoe size 40 returns correct value`() {
        // Given
        val shoeSize = 40
        val crankLength = 170

        // When
        val result = calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 40.0 * 0.6 // 24.0
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with shoe size 45 returns correct value`() {
        // Given
        val shoeSize = 45
        val crankLength = 170

        // When
        val result = calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 45.0 * 0.6 // 27.0
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with null shoe size returns default`() {
        // Given
        val shoeSize = null
        val crankLength = 170

        // When
        val result = calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 42.0 * 0.6 // Default shoe size 42
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with null crank length uses shoe size only`() {
        // Given
        val shoeSize = 42
        val crankLength = null

        // When
        val result = calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 42.0 * 0.6 // 25.2
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with both null returns default`() {
        // Given
        val shoeSize = null
        val crankLength = null

        // When
        val result = calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 42.0 * 0.6 // Default shoe size 42
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with edge case shoe size 30`() {
        // Given
        val shoeSize = 30
        val crankLength = 170

        // When
        val result = calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 30.0 * 0.6 // 18.0
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `invoke with edge case shoe size 50`() {
        // Given
        val shoeSize = 50
        val crankLength = 170

        // When
        val result = calculateCleatPosition(shoeSize, crankLength)

        // Then
        val expected = 50.0 * 0.6 // 30.0
        assertEquals(expected, result, 0.01)
    }
}
