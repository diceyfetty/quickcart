package com.example.quickcart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.quickcart.navigation.AppNavigation
import com.example.quickcart.ui.theme.QuickCartTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
//        enableEdgeToEdge()
        setContent {
            QuickCartTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}


