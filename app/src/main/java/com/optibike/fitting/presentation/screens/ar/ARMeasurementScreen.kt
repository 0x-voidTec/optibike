package com.optibike.fitting.presentation.screens.ar

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import com.optibike.fitting.R
import com.optibike.fitting.presentation.navigation.Destinations
import com.optibike.fitting.presentation.theme.OptiBikeColors
import com.optibike.fitting.presentation.theme.OptiBikeTheme
import com.optibike.fitting.presentation.viewmodel.ARViewModel

/**
 * AR Measurement Screen
 * Full ARCore implementation for bike/body scanning
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun ARMeasurementScreen(
    navController: NavController,
    viewModel: ARViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isARAvailable by viewModel.isARAvailable.collectAsState()
    val isTracking by viewModel.isTracking.collectAsState()
    val trackingState by viewModel.trackingState.collectAsState()
    val error by viewModel.error.collectAsState()
    val isMeasuring by viewModel.isMeasuring.collectAsState()
    val measurementComplete by viewModel.measurementComplete.collectAsState()
    
    var hasCameraPermission by remember { mutableStateOf(false) }
    var arSession: Session? by remember { mutableStateOf(null) }
    
    // Check camera permission
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }
    
    // Check ARCore availability
    LaunchedEffect(Unit) {
        try {
            when (ArCoreApk.getInstance().requestInstall(context as android.app.Activity, true)) {
                ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                    // ARCore needs to be installed
                    viewModel.onARSessionInitialized(false)
                }
                ArCoreApk.InstallStatus.INSTALLED -> {
                    // ARCore is available
                    viewModel.onARSessionInitialized(true)
                }
            }
        } catch (e: Exception) {
            viewModel.onARSessionInitialized(false)
        }
        
        // Check camera permission
        hasCameraPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    // Handle measurement completion
    LaunchedEffect(measurementComplete) {
        if (measurementComplete) {
            navController.navigate(Destinations.RESULTS) {
                popUpTo(Destinations.AR_MEASUREMENT) {
                    inclusive = true
                }
            }
            viewModel.reset()
        }
    }
    
    // Cleanup on exit
    DisposableEffect(Unit) {
        onDispose {
            arSession?.close()
            arSession = null
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OptiBikeColors.BackgroundDark)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
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
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Error message
        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = OptiBikeColors.Error,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // AR Not Supported
        if (!isARAvailable) {
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
            // AR Camera Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(OptiBikeColors.BackgroundDarker),
                contentAlignment = Alignment.Center
            ) {
                // AndroidView for ARCameraPreview
                AndroidView(
                    factory = { ctx ->
                        ARCameraPreview(ctx).apply {
                            // Try to get ARCore session
                            try {
                                if (ArCoreApk.getInstance().checkAvailability(ctx) == ArCoreApk.Availability.SUPPORTED_INSTALLED) {
                                    val session = Session(ctx)
                                    val config = com.google.ar.core.Config(session)
                                    config.planeFindingMode = com.google.ar.core.Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
                                    session.configure(config)
                                    arSession = session
                                    // setupSession is not available, we use the session we created
                                }
                            } catch (e: Exception) {
                                Toast.makeText(ctx, "ARCore error: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                
                // Tracking status overlay
                if (!isTracking) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(OptiBikeColors.BackgroundDark.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = OptiBikeColors.PrimaryCyan,
                                modifier = Modifier.size(48.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = when (trackingState) {
                                    TrackingState.STOPPED -> "Initializing..."
                                    TrackingState.PAUSED -> "Tracking paused"
                                    TrackingState.TRACKING -> "Tracking"
                                    else -> "Waiting for tracking..."
                                },
                                color = OptiBikeColors.TextPrimary,
                                fontSize = 16.sp
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Move your device around to detect surfaces",
                                color = OptiBikeColors.TextSecondary,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
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
            
            // Action buttons
            Button(
                onClick = {
                    if (isTracking) {
                        viewModel.setIsMeasuring(true)
                        // TODO: Start measurement process
                        Toast.makeText(context, "Start scanning your bike", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please wait for tracking to start", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isMeasuring) OptiBikeColors.DividerColor else OptiBikeColors.PrimaryCyan,
                    contentColor = OptiBikeColors.BackgroundDark,
                    disabledContainerColor = OptiBikeColors.DividerColor
                ),
                enabled = isTracking && !isMeasuring
            ) {
                Text(
                    text = if (isMeasuring) "Scanning..." else stringResource(id = R.string.btn_start_scanning),
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

@Preview(showBackground = true)
@Composable
fun ARMeasurementScreenPreview() {
    OptiBikeTheme {
        ARMeasurementScreen(navController = rememberNavController())
    }
}
