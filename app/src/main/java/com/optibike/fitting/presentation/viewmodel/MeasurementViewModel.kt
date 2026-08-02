package com.optibike.fitting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optibike.fitting.domain.model.BikeType
import com.optibike.fitting.domain.model.Measurement
import com.optibike.fitting.domain.model.MeasurementInput
import com.optibike.fitting.domain.repository.MeasurementRepository
import com.optibike.fitting.domain.utils.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Measurement ViewModel
 * Manages measurement input and validation
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltViewModel
class MeasurementViewModel @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : ViewModel() {
    
    // State
    private val _measurementInput = MutableStateFlow(MeasurementInput())
    val measurementInput: StateFlow<MeasurementInput> = _measurementInput.asStateFlow()
    
    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()
    
    // Update input fields
    fun updateHeight(height: Double?) {
        _measurementInput.value = _measurementInput.value.copy(height = height)
        validateInput()
    }
    
    fun updateInseam(inseam: Double?) {
        _measurementInput.value = _measurementInput.value.copy(inseam = inseam)
        validateInput()
    }
    
    fun updateShoulderWidth(shoulderWidth: Double?) {
        _measurementInput.value = _measurementInput.value.copy(shoulderWidth = shoulderWidth)
    }
    
    fun updateArmLength(armLength: Double?) {
        _measurementInput.value = _measurementInput.value.copy(armLength = armLength)
    }
    
    fun updateTorsoLength(torsoLength: Double?) {
        _measurementInput.value = _measurementInput.value.copy(torsoLength = torsoLength)
    }
    
    fun updateBikeType(bikeType: BikeType) {
        _measurementInput.value = _measurementInput.value.copy(bikeType = bikeType)
        validateInput()
    }
    
    fun updateFrameSize(frameSize: Double?) {
        _measurementInput.value = _measurementInput.value.copy(frameSize = frameSize)
    }
    
    fun updateCurrentSaddleHeight(saddleHeight: Double?) {
        _measurementInput.value = _measurementInput.value.copy(currentSaddleHeight = saddleHeight)
    }
    
    fun updateCurrentHandlebarHeight(handlebarHeight: Double?) {
        _measurementInput.value = _measurementInput.value.copy(currentHandlebarHeight = handlebarHeight)
    }
    
    fun updateShoeSize(shoeSize: Int?) {
        _measurementInput.value = _measurementInput.value.copy(shoeSize = shoeSize)
    }
    
    fun updateCrankLength(crankLength: Int?) {
        _measurementInput.value = _measurementInput.value.copy(crankLength = crankLength)
    }
    
    // Validate input
    private fun validateInput() {
        val input = _measurementInput.value
        _validationError.value = Validators.getValidationError(
            input.height,
            input.inseam,
            input.bikeType
        )
    }
    
    // Check if form is valid
    fun isFormValid(): Boolean {
        return _validationError.value == null
    }
    
    // Save measurement
    fun saveMeasurement() {
        if (!isFormValid()) {
            _validationError.value = "Please fill in all required fields"
            return
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val measurement = _measurementInput.value.toMeasurement()
                measurementRepository.saveMeasurement(measurement)
                _saveSuccess.value = true
            } catch (e: Exception) {
                _validationError.value = "Error saving measurement: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    // Reset form
    fun resetForm() {
        _measurementInput.value = MeasurementInput()
        _validationError.value = null
        _saveSuccess.value = false
    }
    
    // Get current input as Measurement
    fun getCurrentMeasurement(): Measurement {
        return _measurementInput.value.toMeasurement()
    }
}
