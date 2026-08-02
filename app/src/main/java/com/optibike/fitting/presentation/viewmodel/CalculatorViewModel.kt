package com.optibike.fitting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optibike.fitting.domain.model.Measurement
import com.optibike.fitting.domain.model.MeasurementResults
import com.optibike.fitting.domain.repository.MeasurementRepository
import com.optibike.fitting.domain.utils.BikeFittingFormulas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Calculator ViewModel
 * Manages bike fitting calculations
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : ViewModel() {
    
    private val _measurement = MutableStateFlow<Measurement?>(null)
    val measurement: StateFlow<Measurement?> = _measurement.asStateFlow()
    
    private val _results = MutableStateFlow<MeasurementResults?>(null)
    val results: StateFlow<MeasurementResults?> = _results.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    /**
     * Load results for a specific measurement ID
     */
    fun loadMeasurementResults(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val measurement = measurementRepository.getMeasurementById(id)
                if (measurement != null) {
                    _measurement.value = measurement
                    if (measurement.isComplete) {
                        _results.value = MeasurementResults(
                            saddleHeight = measurement.calculatedSaddleHeight ?: 0.0,
                            saddleTilt = measurement.calculatedSaddleTilt ?: 0.0,
                            saddleForeAft = measurement.calculatedSaddleForeAft ?: 0.0,
                            handlebarHeight = measurement.calculatedHandlebarHeight ?: 0.0,
                            saddleHandlebarDistance = measurement.calculatedSaddleHandlebarDistance ?: 0.0,
                            handlebarWidth = measurement.calculatedHandlebarWidth ?: 0.0,
                            cleatPosition = measurement.calculatedCleatPosition ?: 0.0,
                            recommendations = BikeFittingFormulas.getLocalizedRecommendations(measurement)
                        )
                    } else {
                        calculateParameters(measurement)
                    }
                } else {
                    _error.value = "Measurement not found"
                }
            } catch (e: Exception) {
                _error.value = "Error loading measurement: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Calculate all parameters for the given measurement
     */
    fun calculateParameters(measurement: Measurement) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _measurement.value = measurement
                val results = BikeFittingFormulas.calculateAllParameters(measurement)
                _results.value = results
                
                // Save calculated results to measurement
                val updatedMeasurement = measurement.copy(
                    calculatedSaddleHeight = results.saddleHeight,
                    calculatedSaddleTilt = results.saddleTilt,
                    calculatedSaddleForeAft = results.saddleForeAft,
                    calculatedHandlebarHeight = results.handlebarHeight,
                    calculatedSaddleHandlebarDistance = results.saddleHandlebarDistance,
                    calculatedHandlebarWidth = results.handlebarWidth,
                    calculatedCleatPosition = results.cleatPosition,
                    isComplete = true
                )
                
                measurementRepository.saveMeasurement(updatedMeasurement)
                _measurement.value = updatedMeasurement
                
            } catch (e: Exception) {
                _error.value = "Error calculating parameters: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Calculate parameters for the latest measurement
     */
    fun calculateLatest() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val latest = measurementRepository.getLatestMeasurement()
                if (latest != null) {
                    calculateParameters(latest)
                } else {
                    _error.value = "No measurements found"
                }
            } catch (e: Exception) {
                _error.value = "Error loading latest measurement: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Clear results
     */
    fun clearResults() {
        _results.value = null
        _measurement.value = null
        _error.value = null
    }
    
    /**
     * Get formatted result value with unit
     */
    fun getFormattedResult(
        value: Double?,
        unit: String = "mm"
    ): String {
        return value?.let { "%.1f $unit".format(it) } ?: "N/A"
    }
    
    /**
     * Get formatted angle value
     */
    fun getFormattedAngle(value: Double?): String {
        return value?.let { "%.1f°".format(it) } ?: "N/A"
    }
}
