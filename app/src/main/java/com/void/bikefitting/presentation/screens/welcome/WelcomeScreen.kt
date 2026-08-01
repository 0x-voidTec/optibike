package com.void.bikefitting.presentation.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.void.bikefitting.R
import com.void.bikefitting.presentation.navigation.Destinations
import com.void.bikefitting.presentation.theme.OptiBikeColors
import com.void.bikefitting.presentation.theme.OptiBikeTheme

/**
 * Welcome Screen
 * Displays app introduction with a call-to-action button
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun WelcomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OptiBikeColors.BackgroundDark)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.optibike_logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .size(100.dp),
            tint = OptiBikeColors.PrimaryCyan
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Title
        Text(
            text = stringResource(id = R.string.onboarding_welcome_title),
            color = OptiBikeColors.TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Subtitle
        Text(
            text = stringResource(id = R.string.onboarding_welcome_subtitle),
            color = OptiBikeColors.TextSecondary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Get Started Button
        Button(
            onClick = {
                navController.navigate(Destinations.LANGUAGE_SELECTION)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = OptiBikeColors.PrimaryCyan,
                contentColor = OptiBikeColors.BackgroundDark
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_get_started),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Learn More Button (Secondary)
        Button(
            onClick = {
                // TODO: Navigate to info screen or open documentation
                navController.navigate(Destinations.GUIDE)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = OptiBikeColors.PrimaryCyan
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Learn More",
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    OptiBikeTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}
