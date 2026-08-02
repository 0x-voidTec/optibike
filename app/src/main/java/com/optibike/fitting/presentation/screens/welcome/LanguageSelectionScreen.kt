package com.optibike.fitting.presentation.screens.welcome

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.optibike.fitting.R
import com.optibike.fitting.presentation.navigation.Destinations
import com.optibike.fitting.presentation.theme.OptiBikeColors
import com.optibike.fitting.presentation.theme.OptiBikeTheme

/**
 * Language Selection Screen
 * Allows user to choose between Polish and English
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun LanguageSelectionScreen(
    navController: NavController
) {
    var selectedLanguage by remember { mutableStateOf("en") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OptiBikeColors.BackgroundDark)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.onboarding_language_title),
            color = OptiBikeColors.TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Language Options
        LanguageOption(
            languageCode = "en",
            languageName = "English",
            flagEmoji = "🇬🇧",
            isSelected = selectedLanguage == "en",
            onClick = { selectedLanguage = "en" }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LanguageOption(
            languageCode = "pl",
            languageName = "Polski",
            flagEmoji = "🇵🇱",
            isSelected = selectedLanguage == "pl",
            onClick = { selectedLanguage = "pl" }
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Continue Button
        Button(
            onClick = {
                // TODO: Save language preference
                // For now, just navigate to Guide
                navController.navigate(Destinations.GUIDE) {
                    popUpTo(Destinations.LANGUAGE_SELECTION) {
                        inclusive = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = OptiBikeColors.PrimaryCyan,
                contentColor = OptiBikeColors.BackgroundDark,
                disabledContainerColor = OptiBikeColors.DividerColor,
                disabledContentColor = OptiBikeColors.TextDisabled
            ),
            enabled = selectedLanguage.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LanguageOption(
    languageCode: String,
    languageName: String,
    flagEmoji: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                if (isSelected) OptiBikeColors.SurfaceDark
                else OptiBikeColors.BackgroundDarker
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Flag
        Text(
            text = flagEmoji,
            fontSize = 24.sp,
            modifier = Modifier.padding(end = 16.dp)
        )
        
        // Language Name
        Text(
            text = languageName,
            color = OptiBikeColors.TextPrimary,
            fontSize = 18.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Selection Indicator
        if (isSelected) {
            Icon(
                imageVector = ImageVector.vectorResource(id = android.R.drawable.radiobutton_on_background),
                contentDescription = "Selected",
                tint = OptiBikeColors.PrimaryCyan,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectionScreenPreview() {
    OptiBikeTheme {
        LanguageSelectionScreen(navController = rememberNavController())
    }
}
