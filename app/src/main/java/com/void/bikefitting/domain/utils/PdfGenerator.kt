package com.void.bikefitting.domain.utils

import android.content.Context
import android.os.Environment
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.void.bikefitting.domain.model.Measurement
import com.void.bikefitting.domain.model.MeasurementResults
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter

/**
 * PDF Generator
 * Generates PDF reports for bike fitting measurements
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class PdfGenerator {
    
    companion object {
        private const val FONT_REGULAR = "Helvetica"
        private const val FONT_BOLD = "Helvetica-Bold"
        private val CYAN_COLOR = com.itextpdf.kernel.colors.DeviceRgb(0, 255, 255)
        private val MAGENTA_COLOR = com.itextpdf.kernel.colors.DeviceRgb(255, 0, 255)
        private val DARK_COLOR = com.itextpdf.kernel.colors.DeviceRgb(10, 10, 15)
        private val TEXT_COLOR = com.itextpdf.kernel.colors.DeviceRgb(224, 224, 224)
    }
    
    /**
     * Generate PDF report for a measurement
     */
    fun generatePdfReport(
        context: Context,
        measurement: Measurement,
        results: MeasurementResults
    ): File? {
        return try {
            // Create output file
            val pdfFile = createOutputFile(context, "OptiBike_Report_${measurement.id}.pdf")
            
            // Initialize PDF writer and document
            val writer = PdfWriter(FileOutputStream(pdfFile))
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)
            
            // Add content
            addHeader(document)
            addMeasurementInfo(document, measurement)
            addResultsTable(document, results)
            addRecommendations(document, results.recommendations)
            addFooter(document)
            
            // Close document
            document.close()
            pdfDoc.close()
            
            pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Create output file in app's external files directory
     */
    private fun createOutputFile(context: Context, fileName: String): File {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            ?: context.filesDir
        
        if (!directory.exists()) {
            directory.mkdirs()
        }
        
        return File(directory, fileName)
    }
    
    /**
     * Add header to PDF
     */
    private fun addHeader(document: Document) {
        val titleFont = PdfFontFactory.createFont(FONT_BOLD)
        val subtitleFont = PdfFontFactory.createFont(FONT_REGULAR)
        
        // Title
        val title = Paragraph("OptiBike - Bike Fitting Report")
            .setFont(titleFont)
            .setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontColor(CYAN_COLOR)
            .setMarginBottom(10f)
        
        document.add(title)
        
        // Subtitle
        val subtitle = Paragraph("Professional Bike Fitting Analysis")
            .setFont(subtitleFont)
            .setFontSize(14f)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontColor(TEXT_COLOR)
            .setMarginBottom(20f)
        
        document.add(subtitle)
        
        // Divider
        document.add(Paragraph(" ").setBorder(SolidBorder(CYAN_COLOR, 1f)))
        document.add(Paragraph(" "))
    }
    
    /**
     * Add measurement information section
     */
    private fun addMeasurementInfo(document: Document, measurement: Measurement) {
        val font = PdfFontFactory.createFont(FONT_REGULAR)
        val boldFont = PdfFontFactory.createFont(FONT_BOLD)
        
        // Section title
        val sectionTitle = Paragraph("Measurement Information")
            .setFont(boldFont)
            .setFontSize(18f)
            .setFontColor(CYAN_COLOR)
            .setMarginBottom(10f)
        
        document.add(sectionTitle)
        
        // Create table for measurement info
        val table = Table(UnitValue.createPercentArray(floatArrayOf(30f, 70f)))
            .setWidth(UnitValue.createPercentValue(100f))
        
        // Bike Type
        addTableRow(table, "Bike Type:", measurement.bikeType.name, font, boldFont)
        
        // Date
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        addTableRow(table, "Date:", measurement.timestamp.format(formatter), font, boldFont)
        
        // Height
        measurement.height?.let { height ->
            addTableRow(table, "Height:", "${height} cm", font, boldFont)
        }
        
        // Inseam
        measurement.inseam?.let { inseam ->
            addTableRow(table, "Inseam:", "${inseam} cm", font, boldFont)
        }
        
        // Shoulder Width
        measurement.shoulderWidth?.let { shoulderWidth ->
            addTableRow(table, "Shoulder Width:", "${shoulderWidth} cm", font, boldFont)
        }
        
        // Arm Length
        measurement.armLength?.let { armLength ->
            addTableRow(table, "Arm Length:", "${armLength} cm", font, boldFont)
        }
        
        document.add(table)
        document.add(Paragraph(" "))
    }
    
    /**
     * Add results table to PDF
     */
    private fun addResultsTable(document: Document, results: MeasurementResults) {
        val font = PdfFontFactory.createFont(FONT_REGULAR)
        val boldFont = PdfFontFactory.createFont(FONT_BOLD)
        
        // Section title
        val sectionTitle = Paragraph("Bike Fitting Results")
            .setFont(boldFont)
            .setFontSize(18f)
            .setFontColor(CYAN_COLOR)
            .setMarginBottom(10f)
        
        document.add(sectionTitle)
        
        // Create table for results
        val table = Table(UnitValue.createPercentArray(floatArrayOf(50f, 30f, 20f)))
            .setWidth(UnitValue.createPercentValue(100f))
        
        // Header row
        addHeaderRow(table, listOf("Parameter", "Value", "Unit"), boldFont)
        
        // Results rows
        addResultRow(table, "Saddle Height", results.saddleHeight, "mm", font)
        addResultRow(table, "Saddle Tilt", results.saddleTilt, "°", font)
        addResultRow(table, "Saddle Fore-Aft", results.saddleForeAft, "mm", font)
        addResultRow(table, "Handlebar Height", results.handlebarHeight, "mm", font)
        addResultRow(table, "Saddle-Handlebar Distance", results.saddleHandlebarDistance, "mm", font)
        addResultRow(table, "Handlebar Width", results.handlebarWidth, "mm", font)
        addResultRow(table, "Cleat Position", results.cleatPosition, "mm", font)
        
        document.add(table)
        document.add(Paragraph(" "))
    }
    
    /**
     * Add recommendations section
     */
    private fun addRecommendations(document: Document, recommendations: List<String>) {
        if (recommendations.isEmpty()) return
        
        val font = PdfFontFactory.createFont(FONT_REGULAR)
        val boldFont = PdfFontFactory.createFont(FONT_BOLD)
        
        // Section title
        val sectionTitle = Paragraph("Recommendations")
            .setFont(boldFont)
            .setFontSize(18f)
            .setFontColor(MAGENTA_COLOR)
            .setMarginBottom(10f)
        
        document.add(sectionTitle)
        
        // Add each recommendation as a bullet point
        recommendations.forEachIndexed { index, recommendation ->
            val bulletPoint = Paragraph("• $recommendation")
                .setFont(font)
                .setFontSize(12f)
                .setFontColor(TEXT_COLOR)
                .setMarginLeft(10f)
                .setMarginBottom(5f)
            document.add(bulletPoint)
        }
        
        document.add(Paragraph(" "))
    }
    
    /**
     * Add footer to PDF
     */
    private fun addFooter(document: Document) {
        val font = PdfFontFactory.createFont(FONT_REGULAR)
        
        val footer = Paragraph("Generated by OptiBike - Professional Bike Fitting App")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontColor(TEXT_COLOR)
            .setMarginTop(20f)
        
        document.add(footer)
    }
    
    /**
     * Helper function to add a table row
     */
    private fun addTableRow(
        table: Table,
        label: String,
        value: String,
        font: PdfFont,
        boldFont: PdfFont
    ) {
        val labelCell = Cell()
            .add(Paragraph(label).setFont(boldFont).setFontSize(12f).setFontColor(TEXT_COLOR))
            .setBorder(SolidBorder(ColorConstants.WHITE, 0.5f))
            .setPadding(5f)
        
        val valueCell = Cell()
            .add(Paragraph(value).setFont(font).setFontSize(12f).setFontColor(CYAN_COLOR))
            .setBorder(SolidBorder(ColorConstants.WHITE, 0.5f))
            .setPadding(5f)
        
        table.addCell(labelCell)
        table.addCell(valueCell)
    }
    
    /**
     * Helper function to add a header row to table
     */
    private fun addHeaderRow(
        table: Table,
        headers: List<String>,
        boldFont: PdfFont
    ) {
        headers.forEach { header ->
            val cell = Cell()
                .add(Paragraph(header).setFont(boldFont).setFontSize(12f).setFontColor(CYAN_COLOR))
                .setBorder(SolidBorder(CYAN_COLOR, 1f))
                .setPadding(5f)
                .setBackgroundColor(DARK_COLOR)
            table.addCell(cell)
        }
    }
    
    /**
     * Helper function to add a result row to table
     */
    private fun addResultRow(
        table: Table,
        parameter: String,
        value: Double,
        unit: String,
        font: PdfFont
    ) {
        val paramCell = Cell()
            .add(Paragraph(parameter).setFont(font).setFontSize(11f).setFontColor(TEXT_COLOR))
            .setBorder(SolidBorder(ColorConstants.WHITE, 0.5f))
            .setPadding(5f)
        
        val valueCell = Cell()
            .add(Paragraph(String.format("%.1f", value)).setFont(font).setFontSize(11f).setFontColor(CYAN_COLOR))
            .setBorder(SolidBorder(ColorConstants.WHITE, 0.5f))
            .setPadding(5f)
        
        val unitCell = Cell()
            .add(Paragraph(unit).setFont(font).setFontSize(11f).setFontColor(TEXT_COLOR))
            .setBorder(SolidBorder(ColorConstants.WHITE, 0.5f))
            .setPadding(5f)
        
        table.addCell(paramCell)
        table.addCell(valueCell)
        table.addCell(unitCell)
    }
}
