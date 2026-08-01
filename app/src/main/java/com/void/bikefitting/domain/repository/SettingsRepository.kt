package com.void.bikefitting.domain.repository

import com.void.bikefitting.domain.model.Settings
import kotlinx.coroutines.flow.Flow

/**
 * Settings Repository Interface
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
interface SettingsRepository {
    
    /**
     * Get current settings
     */
    fun getSettings(): Flow<Settings>
    
    /**
     * Update settings
     */
    suspend fun updateSettings(settings: Settings)
    
    /**
     * Update language
     */
    suspend fun updateLanguage(language: String)
    
    /**
     * Update units
     */
    suspend fun updateUnits(units: String)
    
    /**
     * Update dark mode
     */
    suspend fun updateDarkMode(isEnabled: Boolean)
    
    /**
     * Update measurement precision
     */
    suspend fun updateMeasurementPrecision(precision: Int)
    
    /**
     * Update sounds
     */
    suspend fun updateSounds(areEnabled: Boolean)
}
