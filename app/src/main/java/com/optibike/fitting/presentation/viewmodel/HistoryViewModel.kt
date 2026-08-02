package com.optibike.fitting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optibike.fitting.domain.model.Measurement
import com.optibike.fitting.domain.repository.MeasurementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * History ViewModel
 * Manages measurement history
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : ViewModel() {
    
    private val _measurements = MutableStateFlow<List<Measurement>>(emptyList())
    val measurements: StateFlow<List<Measurement>> = _measurements.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadMeasurements()
    }
    
    /**
     * Load all measurements
     */
    fun loadMeasurements() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                measurementRepository.getAllMeasurements()
                    .collect { measurements ->
                        _measurements.value = measurements
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _error.value = "Error loading measurements: ${e.message}"
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Delete a specific measurement
     */
    fun deleteMeasurement(measurementId: Long) {
        viewModelScope.launch {
            try {
                measurementRepository.deleteMeasurement(measurementId)
                // Refresh the list
                loadMeasurements()
            } catch (e: Exception) {
                _error.value = "Error deleting measurement: ${e.message}"
            }
        }
    }
    
    /**
     * Delete all measurements
     */
    fun deleteAllMeasurements() {
        viewModelScope.launch {
            try {
                measurementRepository.deleteAllMeasurements()
                _measurements.value = emptyList()
            } catch (e: Exception) {
                _error.value = "Error deleting all measurements: ${e.message}"
            }
        }
    }
    
    /**
     * Get measurement by ID
     */
    fun getMeasurementById(id: Long): Measurement? {
        return _measurements.value.find { it.id == id }
    }
}
