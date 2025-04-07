package com.example.firstcomposeproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firstcomposeproject.presentation.splashScreen.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("auth") {
            AuthNavigation(parentNavController = navController)
        }
        composable("main") {
            MainNavigation(parentNavController = navController)
        }
    }
}