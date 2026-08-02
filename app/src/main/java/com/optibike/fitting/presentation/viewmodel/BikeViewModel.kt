package com.optibike.fitting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optibike.fitting.domain.model.Bike
import com.optibike.fitting.domain.repository.BikeRepository
import com.optibike.fitting.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Bike ViewModel
 * Manages bike profiles and selected bike
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltViewModel
class BikeViewModel @Inject constructor(
    private val bikeRepository: BikeRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    // All bikes
    val bikes: StateFlow<List<Bike>> = bikeRepository.getAllBikes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Currently selected bike ID
    val selectedBikeId: StateFlow<Long?> = settingsRepository.getSettings()
        .map { it.selectedBikeId }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun addBike(bike: Bike) {
        viewModelScope.launch {
            bikeRepository.insertBike(bike)
        }
    }

    fun updateBike(bike: Bike) {
        viewModelScope.launch {
            bikeRepository.updateBike(bike)
        }
    }

    fun deleteBike(bike: Bike) {
        viewModelScope.launch {
            bikeRepository.deleteBike(bike)
            // If the deleted bike was selected, deselect it
            if (selectedBikeId.value == bike.id) {
                selectBike(null)
            }
        }
    }

    fun selectBike(bikeId: Long?) {
        viewModelScope.launch {
            settingsRepository.updateSelectedBikeId(bikeId)
        }
    }
}
