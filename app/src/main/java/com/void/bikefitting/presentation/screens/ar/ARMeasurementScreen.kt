package com.void.bikefitting.presentation.screens.ar

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.void.bikefitting.R
import com.void.bikefitting.presentation.navigation.Destinations
import com.void.bikefitting.presentation.theme.OptiBikeColors
import com.void.bikefitting.presentation.theme.OptiBikeTheme

/**
 * AR Measurement Screen
 * Uses ARCore for bike/body scanning (optional feature)
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun ARMeasurementScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(false) }
    var isArSupported by remember { mutableStateOf(true) }
    var isScanning by remember { mutableStateOf(false) }
    
    // Check camera permission
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }
    
    // Check if ARCore is supported
    LaunchedEffect(Unit) {
        // Check ARCore availability
        // For now, assume it's supported if ARCore is available on the device
        // In production, use ArCoreApk.getInstance().checkAvailability()
        isArSupported = true
        
        // Check camera permission
        hasCameraPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
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
            text = stringResource(id = R.string.ar_title),
            color = OptiBikeColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = stringResource(id = R.string.ar_scan_bike),
            color = OptiBikeColors.TextSecondary,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // AR Not Supported
        if (!isArSupported) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.ar_not_supported_title),
                        color = OptiBikeColors.Error,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = stringResource(id = R.string.ar_not_supported_message),
                        color = OptiBikeColors.TextSecondary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = {
                            navController.navigate(Destinations.MANUAL_MEASUREMENT)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OptiBikeColors.PrimaryCyan,
                            contentColor = OptiBikeColors.BackgroundDark
                        )
                    ) {
                        Text(
                            text = "Use Manual Measurement",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        } else if (!hasCameraPermission) {
            // Request camera permission
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.ar_enable_camera),
                        color = OptiBikeColors.Warning,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OptiBikeColors.PrimaryCyan,
                            contentColor = OptiBikeColors.BackgroundDark
                        )
                    ) {
                        Text(
                            text = "Enable Camera",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        } else {
            // AR Scanning UI
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(OptiBikeColors.BackgroundDarker),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Camera preview placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(OptiBikeColors.BackgroundDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Camera Preview",
                            color = OptiBikeColors.TextSecondary,
                            fontSize = 16.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Instructions
                    Text(
                        text = stringResource(id = R.string.ar_instructions),
                        color = OptiBikeColors.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "${stringResource(id = R.string.ar_instruction_1)}\n${stringResource(id = R.string.ar_instruction_2)}\n${stringResource(id = R.string.ar_instruction_3)}\n${stringResource(id = R.string.ar_instruction_4)}",
                        color = OptiBikeColors.TextSecondary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Scan button
                    Button(
                        onClick = {
                            isScanning = true
                            // TODO: Start AR scanning
                            // For now, simulate scanning and navigate to results
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isScanning) OptiBikeColors.DividerColor else OptiBikeColors.PrimaryCyan,
                            contentColor = OptiBikeColors.BackgroundDark,
                            disabledContainerColor = OptiBikeColors.DividerColor
                        ),
                        enabled = !isScanning
                    ) {
                        Text(
                            text = if (isScanning) "Scanning..." else stringResource(id = R.string.btn_start_scanning),
                            fontSize = 16.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = OptiBikeColors.TextSecondary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.btn_cancel),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ARMeasurementScreenPreview() {
    OptiBikeTheme {
        ARMeasurementScreen(navController = rememberNavController())
    }
}
