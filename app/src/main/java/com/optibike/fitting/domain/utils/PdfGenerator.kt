package com.optibike.fitting.domain.utils

import android.content.Context
import android.os.Environment
import com.optibike.fitting.domain.model.Measurement
import com.optibike.fitting.domain.model.MeasurementResults
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter

/**
 * PDF Generator using Apache PDFBox
 * Generates PDF reports for bike fitting measurements
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class PdfGenerator {
    
    companion object {
        private const val FONT_SIZE_TITLE = 16f
        private const val FONT_SIZE_NORMAL = 12f
        private const val FONT_SIZE_SMALL = 10f
        private const val MARGIN = 50f
        private const val LINE_SPACING = 15f
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
            
            // Create PDF document
            val document = PDDocument()
            val page = PDPage(PDRectangle.A4)
            document.addPage(page)
            
            // Create content stream
            val contentStream = PDPageContentStream(document, page)
            
            // Start writing content
            var yPosition = page.mediaBox.height - MARGIN
            
            // Title
            writeText(contentStream, "OptiBike - Bike Fitting Report", 
                MARGIN, yPosition, FONT_SIZE_TITLE * 1.5f, PDType1Font.HELVETICA_BOLD)
            yPosition -= LINE_SPACING * 1.5f
            
            writeText(contentStream, "Professional Bike Fitting Analysis", 
                MARGIN, yPosition, FONT_SIZE_TITLE, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING * 2f
            
            // Divider line
            drawHorizontalLine(contentStream, MARGIN, page.mediaBox.width - MARGIN, yPosition)
            yPosition -= LINE_SPACING * 2f
            
            // Measurement Information
            writeText(contentStream, "Measurement Information", 
                MARGIN, yPosition, FONT_SIZE_TITLE, PDType1Font.HELVETICA_BOLD)
            yPosition -= LINE_SPACING * 1.5f
            
            writeText(contentStream, "Bike Type: ${measurement.bikeType.name}", 
                MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING
            
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            writeText(contentStream, "Date: ${measurement.timestamp.format(formatter)}", 
                MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING
            
            measurement.height?.let { height ->
                writeText(contentStream, "Height: ${height}cm", 
                    MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
                yPosition -= LINE_SPACING
            }
            
            measurement.inseam?.let { inseam ->
                writeText(contentStream, "Inseam: ${inseam}cm", 
                    MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
                yPosition -= LINE_SPACING
            }
            
            yPosition -= LINE_SPACING
            
            // Results
            writeText(contentStream, "Bike Fitting Results", 
                MARGIN, yPosition, FONT_SIZE_TITLE, PDType1Font.HELVETICA_BOLD)
            yPosition -= LINE_SPACING * 1.5f
            
            writeText(contentStream, "Saddle Height: ${results.saddleHeight.toInt()}mm", 
                MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING
            
            writeText(contentStream, "Saddle Tilt: ${results.saddleTilt}°", 
                MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING
            
            writeText(contentStream, "Handlebar Height: ${results.handlebarHeight.toInt()}mm", 
                MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING
            
            writeText(contentStream, "Saddle-Handlebar Distance: ${results.saddleHandlebarDistance.toInt()}mm", 
                MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING
            
            writeText(contentStream, "Handlebar Width: ${results.handlebarWidth.toInt()}mm", 
                MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING
            
            writeText(contentStream, "Cleat Position: ${results.cleatPosition.toInt()}mm", 
                MARGIN, yPosition, FONT_SIZE_NORMAL, PDType1Font.HELVETICA)
            yPosition -= LINE_SPACING
            
            // Recommendations
            if (results.recommendations.isNotEmpty()) {
                yPosition -= LINE_SPACING
                writeText(contentStream, "Recommendations:", 
                    MARGIN, yPosition, FONT_SIZE_TITLE, PDType1Font.HELVETICA_BOLD)
                yPosition -= LINE_SPACING * 1.5f
                
                results.recommendations.forEach { recommendation ->
                    writeText(contentStream, "• $recommendation", 
                        MARGIN + 20, yPosition, FONT_SIZE_SMALL, PDType1Font.HELVETICA)
                    yPosition -= LINE_SPACING
                }
            }
            
            // Footer
            yPosition -= LINE_SPACING * 2f
            drawHorizontalLine(contentStream, MARGIN, page.mediaBox.width - MARGIN, yPosition)
            yPosition -= LINE_SPACING
            
            writeText(contentStream, "Generated by OptiBike - Professional Bike Fitting App", 
                MARGIN, yPosition, FONT_SIZE_SMALL, PDType1Font.HELVETICA_OBLIQUE)
            
            // Close content stream and document
            contentStream.close()
            document.save(pdfFile)
            document.close()
            
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
     * Write text to PDF content stream
     */
    private fun writeText(
        contentStream: PDPageContentStream,
        text: String,
        x: Float,
        y: Float,
        fontSize: Float,
        font: PDType1Font
    ) {
        contentStream.beginText()
        contentStream.setFont(font, fontSize)
        contentStream.newLineAtOffset(x, y)
        contentStream.showText(text)
        contentStream.endText()
    }
    
    /**
     * Draw horizontal line
     */
    private fun drawHorizontalLine(
        contentStream: PDPageContentStream,
        xStart: Float,
        xEnd: Float,
        y: Float
    ) {
        contentStream.setStrokingColor(200, 200, 200)
        contentStream.setLineWidth(0.5f)
        contentStream.moveTo(xStart, y)
        contentStream.lineTo(xEnd, y)
        contentStream.stroke()
    }
}
