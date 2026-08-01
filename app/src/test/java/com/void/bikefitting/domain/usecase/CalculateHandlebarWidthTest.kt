package com.void.bikefitting.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for CalculateHandlebarWidth use case
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class CalculateHandlebarWidthTest {

    private val calculateHandlebarWidth = CalculateHandlebarWidth()

    @Test
    fun `invoke with shoulder width 45 returns 45`() {
        // Given
        val shoulderWidth = 45.0

        // When
        val result = calculateHandlebarWidth(shoulderWidth)

        // Then
        assertEquals(45.0, result, 0.01)
    }

    @Test
    fun `invoke with shoulder width 40 returns 40`() {
        // Given
        val shoulderWidth = 40.0

        // When
        val result = calculateHandlebarWidth(shoulderWidth)

        // Then
        assertEquals(40.0, result, 0.01)
    }

    @Test
    fun `invoke with shoulder width 50 returns 50`() {
        // Given
        val shoulderWidth = 50.0

        // When
        val result = calculateHandlebarWidth(shoulderWidth)

        // Then
        assertEquals(50.0, result, 0.01)
    }

    @Test
    fun `invoke with null shoulder width returns default 42`() {
        // Given
        val shoulderWidth = null

        // When
        val result = calculateHandlebarWidth(shoulderWidth)

        // Then
        assertEquals(42.0, result, 0.01)
    }

    @Test
    fun `invoke with shoulder width 35 returns 35`() {
        // Given
        val shoulderWidth = 35.0

        // When
        val result = calculateHandlebarWidth(shoulderWidth)

        // Then
        assertEquals(35.0, result, 0.01)
    }

    @Test
    fun `invoke with shoulder width 60 returns 60`() {
        // Given
        val shoulderWidth = 60.0

        // When
        val result = calculateHandlebarWidth(shoulderWidth)

        // Then
        assertEquals(60.0, result, 0.01)
    }
}
