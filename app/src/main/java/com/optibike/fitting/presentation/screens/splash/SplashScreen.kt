package com.optibike.fitting.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import kotlinx.coroutines.delay

/**
 * Splash Screen
 * Displays the OptiBike logo with animation and transitions to Welcome screen
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun SplashScreen(
    navController: NavController
) {
    val scale = remember { Animatable(0.5f) }
    
    LaunchedEffect(Unit) {
        // Animate logo
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        
        // Wait and navigate to Welcome
        delay(2000)
        navController.navigate(Destinations.WELCOME) {
            popUpTo(Destinations.SPLASH) {
                inclusive = true
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OptiBikeColors.BackgroundDark),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.optibike_logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .size(120.dp)
                .scale(scale.value),
            colorFilter = ColorFilter.tint(OptiBikeColors.PrimaryCyan)
        )
        
        // App Name
        Text(
            text = stringResource(id = R.string.app_name),
            color = OptiBikeColors.PrimaryCyan,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 16.dp)
        )
        
        // Tagline
        Text(
            text = stringResource(id = R.string.app_tagline),
            color = OptiBikeColors.TextSecondary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    OptiBikeTheme {
        SplashScreen(navController = rememberNavController())
    }
}
