package com.example.firstcomposeproject.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.firstcomposeproject.presentation.navigation.AppNavigation
import com.example.firstcomposeproject.ui.theme.FirstComposeProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeProjectTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//        FirstScreen(onNavigateToSecondScreen = {})
//
//}