package com.void.bikefitting.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.void.bikefitting.presentation.navigation.BottomNavItem
import com.void.bikefitting.presentation.navigation.Destinations
import com.void.bikefitting.presentation.theme.OptiBikeColors
import com.void.bikefitting.presentation.theme.OptiBikeTheme

/**
 * Bottom Navigation Bar Component
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Only show bottom nav on main screens
    val shouldShowBottomNav = currentRoute in Destinations.BOTTOM_NAV_ITEMS.map { it.route }
    
    if (shouldShowBottomNav) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(OptiBikeColors.BackgroundDarker),
            containerColor = OptiBikeColors.BackgroundDarker,
            contentColor = OptiBikeColors.TextSecondary
        ) {
            Destinations.BOTTOM_NAV_ITEMS.forEach { item ->
                val isSelected = currentRoute == item.route
                
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = stringResource(id = item.titleResId),
                            tint = if (isSelected) OptiBikeColors.PrimaryCyan else OptiBikeColors.TextSecondary
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.titleResId),
                            color = if (isSelected) OptiBikeColors.PrimaryCyan else OptiBikeColors.TextSecondary,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = OptiBikeColors.PrimaryCyan,
                        unselectedIconColor = OptiBikeColors.TextSecondary,
                        selectedTextColor = OptiBikeColors.PrimaryCyan,
                        unselectedTextColor = OptiBikeColors.TextSecondary,
                        indicatorColor = OptiBikeColors.PrimaryCyan.copy(alpha = 0.1f)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    OptiBikeTheme {
        BottomNavigationBar(navController = rememberNavController())
    }
}
