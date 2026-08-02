@file:OptIn(ExperimentalMaterial3Api::class)
package com.optibike.fitting.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.optibike.fitting.R
import com.optibike.fitting.presentation.navigation.Destinations
import com.optibike.fitting.presentation.theme.OptiBikeColors
import com.optibike.fitting.presentation.theme.OptiBikeTheme
import com.optibike.fitting.presentation.viewmodel.SettingsViewModel

/**
 * Settings Screen
 * App settings including language, units, dark mode, etc.
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    
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
            text = stringResource(id = R.string.settings_title),
            color = OptiBikeColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Settings Sections
        SettingsSection(title = "General") {
            // Language
            LanguageSetting(
                currentLanguage = settings.language,
                onLanguageChange = { language ->
                    viewModel.updateLanguage(language)
                }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Units
            UnitsSetting(
                currentUnits = settings.units,
                onUnitsChange = { units ->
                    viewModel.updateUnits(units)
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SettingsSection(title = "Display") {
            // Dark Mode
            DarkModeSetting(
                isDarkModeEnabled = settings.isDarkModeEnabled,
                onDarkModeChange = { isEnabled ->
                    viewModel.updateDarkMode(isEnabled)
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SettingsSection(title = "Measurement") {
            // Measurement Precision
            PrecisionSetting(
                currentPrecision = settings.measurementPrecision,
                onPrecisionChange = { precision ->
                    viewModel.updateMeasurementPrecision(precision)
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SettingsSection(title = "About") {
            AboutInfo()
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
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
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = OptiBikeColors.CardBackground,
            contentColor = OptiBikeColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = OptiBikeColors.PrimaryCyan,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            content()
        }
    }
}

@Composable
private fun LanguageSetting(
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.settings_language),
            color = OptiBikeColors.TextPrimary,
            fontSize = 14.sp
        )
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Text(
                text = when (currentLanguage) {
                    "en" -> stringResource(id = R.string.settings_language_english)
                    "pl" -> stringResource(id = R.string.settings_language_polish)
                    else -> currentLanguage
                },
                color = OptiBikeColors.TextSecondary,
                fontSize = 14.sp,
                modifier = Modifier.menuAnchor()
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.settings_language_english)) },
                    onClick = {
                        onLanguageChange("en")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.settings_language_polish)) },
                    onClick = {
                        onLanguageChange("pl")
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun UnitsSetting(
    currentUnits: String,
    onUnitsChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.settings_units),
            color = OptiBikeColors.TextPrimary,
            fontSize = 14.sp
        )
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Text(
                text = when (currentUnits) {
                    "metric" -> stringResource(id = R.string.settings_units_metric)
                    "imperial" -> stringResource(id = R.string.settings_units_imperial)
                    else -> currentUnits
                },
                color = OptiBikeColors.TextSecondary,
                fontSize = 14.sp,
                modifier = Modifier.menuAnchor()
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.settings_units_metric)) },
                    onClick = {
                        onUnitsChange("metric")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.settings_units_imperial)) },
                    onClick = {
                        onUnitsChange("imperial")
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DarkModeSetting(
    isDarkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDarkModeChange(!isDarkModeEnabled) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.settings_dark_mode),
            color = OptiBikeColors.TextPrimary,
            fontSize = 14.sp
        )
        
        Switch(
            checked = isDarkModeEnabled,
            onCheckedChange = onDarkModeChange,
            thumbContent = {
                Icon(
                    imageVector = if (isDarkModeEnabled) {
                        Icons.Default.NightlightRound
                    } else {
                        Icons.Default.WbSunny
                    },
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (isDarkModeEnabled) OptiBikeColors.PrimaryCyan else OptiBikeColors.PrimaryMagenta
                )
            }
        )
    }
}

@Composable
private fun PrecisionSetting(
    currentPrecision: Int,
    onPrecisionChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.settings_measurement_precision),
            color = OptiBikeColors.TextPrimary,
            fontSize = 14.sp
        )
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Text(
                text = "$currentPrecision decimal places",
                color = OptiBikeColors.TextSecondary,
                fontSize = 14.sp,
                modifier = Modifier.menuAnchor()
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                (0..3).forEach { precision ->
                    DropdownMenuItem(
                        text = { Text("$precision decimal places") },
                        onClick = {
                            onPrecisionChange(precision)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AboutInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "${stringResource(id = R.string.settings_version)}: 1.0.0",
            color = OptiBikeColors.TextSecondary,
            fontSize = 14.sp
        )
        
        Text(
            text = "OptiBike - Professional Bike Fitting App",
            color = OptiBikeColors.TextSecondary,
            fontSize = 12.sp
        )
        
        Text(
            text = "© 2026 0x-void Dev Team",
            color = OptiBikeColors.TextSecondary,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    OptiBikeTheme {
        SettingsScreen(navController = rememberNavController())
    }
}
