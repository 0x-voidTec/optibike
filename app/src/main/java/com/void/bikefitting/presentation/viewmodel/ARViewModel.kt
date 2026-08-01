package com.void.bikefitting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ar.core.Anchor
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.core.TrackingState
import com.void.bikefitting.domain.model.Measurement
import com.void.bikefitting.domain.repository.MeasurementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AR ViewModel
 * Manages AR measurement state and data
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltViewModel
class ARViewModel @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : ViewModel() {
    
    // AR State
    private val _isARAvailable = MutableStateFlow(false)
    val isARAvailable: StateFlow<Boolean> = _isARAvailable.asStateFlow()
    
    private val _isTracking = MutableStateFlow(false)
    val isTracking: StateFlow<Boolean> = _isTracking.asStateFlow()
    
    private val _trackingState = MutableStateFlow<TrackingState>(TrackingState.STOPPED)
    val trackingState: StateFlow<TrackingState> = _trackingState.asStateFlow()
    
    private val _planes = MutableStateFlow<List<Plane>>(emptyList())
    val planes: StateFlow<List<Plane>> = _planes.asStateFlow()
    
    private val _selectedPlane = MutableStateFlow<Plane?>(null)
    val selectedPlane: StateFlow<Plane?> = _selectedPlane.asStateFlow()
    
    // Measurement State
    private val _saddleHeightAnchor = MutableStateFlow<Anchor?>(null)
    val saddleHeightAnchor: StateFlow<Anchor?> = _saddleHeightAnchor.asStateFlow()
    
    private val _handlebarHeightAnchor = MutableStateFlow<Anchor?>(null)
    val handlebarHeightAnchor: StateFlow<Anchor?> = _handlebarHeightAnchor.asStateFlow()
    
    private val _saddleHeight = MutableStateFlow<Double?>(null)
    val saddleHeight: StateFlow<Double?> = _saddleHeight.asStateFlow()
    
    private val _handlebarHeight = MutableStateFlow<Double?>(null)
    val handlebarHeight: StateFlow<Double?> = _handlebarHeight.asStateFlow()
    
    private val _saddleHandlebarDistance = MutableStateFlow<Double?>(null)
    val saddleHandlebarDistance: StateFlow<Double?> = _saddleHandlebarDistance.asStateFlow()
    
    // Error and Status
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _isMeasuring = MutableStateFlow(false)
    val isMeasuring: StateFlow<Boolean> = _isMeasuring.asStateFlow()
    
    private val _measurementComplete = MutableStateFlow(false)
    val measurementComplete: StateFlow<Boolean> = _measurementComplete.asStateFlow()
    
    // AR Session callbacks
    fun onARSessionInitialized(isAvailable: Boolean) {
        _isARAvailable.value = isAvailable
    }
    
    fun onTrackingStateChanged(trackingState: TrackingState) {
        _trackingState.value = trackingState
        _isTracking.value = trackingState == TrackingState.TRACKING
    }
    
    fun onPlanesDetected(planes: List<Plane>) {
        _planes.value = planes
    }
    
    fun onPlaneSelected(plane: Plane?) {
        _selectedPlane.value = plane
    }
    
    // Measurement functions
    fun setSaddleHeightAnchor(anchor: Anchor?) {
        _saddleHeightAnchor.value = anchor
        anchor?.let {
            // Calculate height from camera pose
            // This is a simplified calculation
            // In production, you would use the actual pose and plane information
        }
    }
    
    fun setHandlebarHeightAnchor(anchor: Anchor?) {
        _handlebarHeightAnchor.value = anchor
    }
    
    fun setSaddleHeight(height: Double) {
        _saddleHeight.value = height
        calculateDistance()
    }
    
    fun setHandlebarHeight(height: Double) {
        _handlebarHeight.value = height
        calculateDistance()
    }
    
    private fun calculateDistance() {
        val saddle = _saddleHeight.value
        val handlebar = _handlebarHeight.value
        
        if (saddle != null && handlebar != null) {
            _saddleHandlebarDistance.value = saddle - handlebar
        }
    }
    
    // Save measurement
    fun saveMeasurement() {
        viewModelScope.launch {
            try {
                val measurement = Measurement(
                    height = null, // Will be set from user input
                    inseam = null,
                    bikeType = com.void.bikefitting.domain.model.BikeType.ROAD,
                    currentSaddleHeight = _saddleHeight.value,
                    currentHandlebarHeight = _handlebarHeight.value,
                    calculatedSaddleHeight = _saddleHeight.value,
                    calculatedHandlebarHeight = _handlebarHeight.value,
                    calculatedSaddleHandlebarDistance = _saddleHandlebarDistance.value,
                    isComplete = true
                )
                
                measurementRepository.saveMeasurement(measurement)
                _measurementComplete.value = true
            } catch (e: Exception) {
                _error.value = "Error saving measurement: ${e.message}"
            }
        }
    }
    
    // Reset
    fun reset() {
        _saddleHeightAnchor.value = null
        _handlebarHeightAnchor.value = null
        _saddleHeight.value = null
        _handlebarHeight.value = null
        _saddleHandlebarDistance.value = null
        _selectedPlane.value = null
        _error.value = null
        _isMeasuring.value = false
        _measurementComplete.value = false
    }
    
    // Calculate height from pose (simplified)
    fun calculateHeightFromPose(cameraPose: Pose, planePose: Pose): Double {
        // Calculate the distance between camera and plane
        val cameraPosition = cameraPose.translation
        val planePosition = planePose.translation
        
        // Simple distance calculation (in meters)
        val dx = cameraPosition.x() - planePosition.x()
        val dy = cameraPosition.y() - planePosition.y()
        val dz = cameraPosition.z() - planePosition.z()
        
        val distance = Math.sqrt(dx * dx + dy * dy + dz * dz)
        
        // Convert to mm and return
        return distance * 1000
    }
}
