package com.void.bikefitting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void.bikefitting.domain.model.Settings
import com.void.bikefitting.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Settings ViewModel
 * Manages app settings
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    
    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings.asStateFlow()
    
    init {
        loadSettings()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                _settings.value = settings
            }
        }
    }
    
    fun updateLanguage(language: String) {
        viewModelScope.launch {
            settingsRepository.updateLanguage(language)
        }
    }
    
    fun updateUnits(units: String) {
        viewModelScope.launch {
            settingsRepository.updateUnits(units)
        }
    }
    
    fun updateDarkMode(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDarkMode(isEnabled)
        }
    }
    
    fun updateMeasurementPrecision(precision: Int) {
        viewModelScope.launch {
            settingsRepository.updateMeasurementPrecision(precision)
        }
    }
    
    fun updateSounds(areEnabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateSounds(areEnabled)
        }
    }
}
