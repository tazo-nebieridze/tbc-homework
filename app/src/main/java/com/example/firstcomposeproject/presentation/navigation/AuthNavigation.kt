package com.example.firstcomposeproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firstcomposeproject.presentation.auth.logIn.LoginScreen
import com.example.firstcomposeproject.presentation.auth.register.RegisterScreen

@Composable
fun AuthNavigation(parentNavController: NavHostController) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { backStackEntry ->
            val onRegisterClick = { navController.navigate("register") }
            val onLoginSuccess = {
                parentNavController.navigate("main") {
                    popUpTo("auth") { inclusive = true }
                }
            }
            val registeredCredentials = backStackEntry.savedStateHandle
                .get<Map<String, String>?>("registered_credentials")
            LoginScreen(
                registeredCredentials = registeredCredentials,
                onRegisterClick = onRegisterClick,
                onLoginSuccess = onLoginSuccess
            )
        }
        composable("register") {
            val onRegisterSuccess: (String, String) -> Unit = { email, password ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "registered_credentials",
                    mapOf("email" to email, "password" to password)
                )
                navController.popBackStack()
            }
            RegisterScreen(
                onRegisterSuccess = onRegisterSuccess,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}