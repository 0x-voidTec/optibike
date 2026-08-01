package com.void.bikefitting.presentation.screens.results

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.void.bikefitting.R
import com.void.bikefitting.presentation.navigation.Destinations
import com.void.bikefitting.presentation.theme.OptiBikeColors
import com.void.bikefitting.presentation.theme.OptiBikeTheme
import com.void.bikefitting.presentation.viewmodel.PdfExportViewModel
import java.io.File

/**
 * PDF Export Screen
 * Generates and exports PDF reports
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun PdfExportScreen(
    navController: NavController,
    viewModel: PdfExportViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val pdfFile by viewModel.pdfFile.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()
    
    // Handle successful PDF generation
    LaunchedEffect(success) {
        if (success && pdfFile != null) {
            // Share the PDF file
            sharePdf(context, pdfFile!!)
            viewModel.clearState()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OptiBikeColors.BackgroundDark)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.pdf_title),
            color = OptiBikeColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Generate and share your bike fitting report",
            color = OptiBikeColors.TextSecondary,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Loading indicator
        if (isGenerating) {
            CircularProgressIndicator(
                color = OptiBikeColors.PrimaryCyan,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Generating PDF...",
                color = OptiBikeColors.TextSecondary,
                fontSize = 16.sp
            )
        } else if (error != null) {
            // Error message
            Text(
                text = error!!,
                color = OptiBikeColors.Error,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            // Description
            Text(
                text = "Your bike fitting report will include:",
                color = OptiBikeColors.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Features list
            FeatureItem(text = "Measurement details")
            FeatureItem(text = "Calculated parameters")
            FeatureItem(text = "Expert recommendations")
            FeatureItem(text = "Professional formatting")
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Generate PDF Button
        Button(
            onClick = {
                viewModel.generatePdfForLatest(context)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = OptiBikeColors.PrimaryCyan,
                contentColor = OptiBikeColors.BackgroundDark,
                disabledContainerColor = OptiBikeColors.DividerColor,
                disabledContentColor = OptiBikeColors.TextDisabled
            ),
            enabled = !isGenerating,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_export_pdf),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Back Button
        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = OptiBikeColors.TextSecondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_back),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun FeatureItem(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "• $text",
            color = OptiBikeColors.TextSecondary,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

/**
 * Share PDF file using Android's share intent
 */
private fun sharePdf(context: android.content.Context, file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
    
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_SUBJECT, "OptiBike - Bike Fitting Report")
        putExtra(Intent.EXTRA_TEXT, "Check out my bike fitting report generated with OptiBike!")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    
    context.startActivity(
        Intent.createChooser(
            intent,
            "Share Bike Fitting Report"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PdfExportScreenPreview() {
    OptiBikeTheme {
        PdfExportScreen(navController = rememberNavController())
    }
}
