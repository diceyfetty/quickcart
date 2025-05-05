package com.example.quickcart.ui.theme.screens.userprofile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quickcart.data.UserRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val userRepo = remember { UserRepository() }
    val userEmail = userRepo.getCurrentUserEmail()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Profile") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Logged in as", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(userEmail ?: "Unknown")

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                userRepo.logout {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }) {
                Text("Logout")
            }
        }
    }
}