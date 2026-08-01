package com.void.bikefitting.domain.model

/**
 * App Settings Model
 * Stores user preferences
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
data class Settings(
    val language: String = "en", // "en" or "pl"
    val units: String = "metric", // "metric" or "imperial"
    val isDarkModeEnabled: Boolean = true,
    val measurementPrecision: Int = 1, // 0-3 decimal places
    val areSoundsEnabled: Boolean = true
)
