package com.example.firstcomposeproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firstcomposeproject.presentation.splash.SplashScreen
import com.example.firstcomposeproject.presentation.auth.logIn.LoginScreen
import com.example.firstcomposeproject.presentation.auth.register.RegisterScreen
import com.example.firstcomposeproject.presentation.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "SplashScreen"
    ) {
        composable("SplashScreen") {
            SplashScreen(navController = navController)
        }
        composable("LoginScreen") {
            LoginScreen(
                navController = navController,
                onRegisterClick = { navController.navigate("RegisterScreen") }
            )
        }
        composable("RegisterScreen") {
            RegisterScreen(
                navController = navController,

                )
        }
        composable("HomeScreen") {
            HomeScreen()
        }
    }
}
