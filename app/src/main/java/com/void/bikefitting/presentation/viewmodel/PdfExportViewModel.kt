package com.void.bikefitting.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void.bikefitting.domain.model.Measurement
import com.void.bikefitting.domain.model.MeasurementResults
import com.void.bikefitting.domain.repository.MeasurementRepository
import com.void.bikefitting.domain.utils.BikeFittingFormulas
import com.void.bikefitting.domain.utils.PdfGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * PDF Export ViewModel
 * Manages PDF generation and export
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltViewModel
class PdfExportViewModel @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : ViewModel() {
    
    private val _pdfFile = MutableStateFlow<File?>(null)
    val pdfFile: StateFlow<File?> = _pdfFile.asStateFlow()
    
    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success.asStateFlow()
    
    private val pdfGenerator = PdfGenerator()
    
    /**
     * Generate PDF for the latest measurement
     */
    fun generatePdfForLatest(context: Context) {
        viewModelScope.launch {
            _isGenerating.value = true
            _error.value = null
            _success.value = false
            
            try {
                val latest = measurementRepository.getLatestMeasurement()
                if (latest != null) {
                    val results = BikeFittingFormulas.calculateAllParameters(latest)
                    generatePdf(context, latest, results)
                } else {
                    _error.value = "No measurements found to export"
                }
            } catch (e: Exception) {
                _error.value = "Error generating PDF: ${e.message}"
            } finally {
                _isGenerating.value = false
            }
        }
    }
    
    /**
     * Generate PDF for a specific measurement
     */
    fun generatePdfForMeasurement(
        context: Context,
        measurementId: Long
    ) {
        viewModelScope.launch {
            _isGenerating.value = true
            _error.value = null
            _success.value = false
            
            try {
                val measurement = measurementRepository.getMeasurementById(measurementId)
                if (measurement != null) {
                    val results = BikeFittingFormulas.calculateAllParameters(measurement)
                    generatePdf(context, measurement, results)
                } else {
                    _error.value = "Measurement not found"
                }
            } catch (e: Exception) {
                _error.value = "Error generating PDF: ${e.message}"
            } finally {
                _isGenerating.value = false
            }
        }
    }
    
    /**
     * Generate PDF file
     */
    private fun generatePdf(
        context: Context,
        measurement: Measurement,
        results: MeasurementResults
    ) {
        val file = pdfGenerator.generatePdfReport(context, measurement, results)
        if (file != null) {
            _pdfFile.value = file
            _success.value = true
        } else {
            _error.value = "Failed to generate PDF file"
        }
    }
    
    /**
     * Clear state
     */
    fun clearState() {
        _pdfFile.value = null
        _error.value = null
        _success.value = false
    }
    
    /**
     * Get PDF file path for sharing
     */
    fun getPdfFilePath(): String? {
        return _pdfFile.value?.absolutePath
    }
}
