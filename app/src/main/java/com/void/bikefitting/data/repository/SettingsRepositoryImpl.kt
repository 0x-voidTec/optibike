package com.void.bikefitting.data.repository

import android.content.SharedPreferences
import com.void.bikefitting.domain.model.Settings
import com.void.bikefitting.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Settings Repository Implementation
 * Uses SharedPreferences to store settings
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SettingsRepository {
    
    private val _settings = MutableStateFlow(loadSettings())
    override val settings: Flow<Settings> = _settings.asStateFlow()
    
    private fun loadSettings(): Settings {
        return Settings(
            language = sharedPreferences.getString("language", "en") ?: "en",
            units = sharedPreferences.getString("units", "metric") ?: "metric",
            isDarkModeEnabled = sharedPreferences.getBoolean("dark_mode", true),
            measurementPrecision = sharedPreferences.getInt("precision", 1),
            areSoundsEnabled = sharedPreferences.getBoolean("sounds", true)
        )
    }
    
    override suspend fun updateSettings(settings: Settings) {
        _settings.update { settings }
        saveSettings(settings)
    }
    
    override suspend fun updateLanguage(language: String) {
        val current = _settings.value
        _settings.update { current.copy(language = language) }
        sharedPreferences.edit().putString("language", language).apply()
    }
    
    override suspend fun updateUnits(units: String) {
        val current = _settings.value
        _settings.update { current.copy(units = units) }
        sharedPreferences.edit().putString("units", units).apply()
    }
    
    override suspend fun updateDarkMode(isEnabled: Boolean) {
        val current = _settings.value
        _settings.update { current.copy(isDarkModeEnabled = isEnabled) }
        sharedPreferences.edit().putBoolean("dark_mode", isEnabled).apply()
    }
    
    override suspend fun updateMeasurementPrecision(precision: Int) {
        val current = _settings.value
        _settings.update { current.copy(measurementPrecision = precision) }
        sharedPreferences.edit().putInt("precision", precision).apply()
    }
    
    override suspend fun updateSounds(areEnabled: Boolean) {
        val current = _settings.value
        _settings.update { current.copy(areSoundsEnabled = areEnabled) }
        sharedPreferences.edit().putBoolean("sounds", areEnabled).apply()
    }
    
    private fun saveSettings(settings: Settings) {
        sharedPreferences.edit()
            .putString("language", settings.language)
            .putString("units", settings.units)
            .putBoolean("dark_mode", settings.isDarkModeEnabled)
            .putInt("precision", settings.measurementPrecision)
            .putBoolean("sounds", settings.areSoundsEnabled)
            .apply()
    }
}
