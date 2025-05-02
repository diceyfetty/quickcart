package com.example.quickcart

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quickcart.data.AuthRepository
import com.example.quickcart.navigation.Routes

@Composable
fun SplashScreen(navController: NavController){
    val context = LocalContext.current
    val authRepo = remember { AuthRepository() }
//    val splashScreenDuration = 3000L
    LaunchedEffect(true) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (authRepo.currentUser != null){
                navController.navigate(Routes.HOME){
                    popUpTo(Routes.SPLASH)
                    {inclusive = true}
                }
            }else {
                navController.navigate(Routes.LOGIN){
                    popUpTo(Routes.SPLASH)
                    {inclusive = true}
                }
            }

        }, 2000)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)
        .background(Color.Black),
        contentAlignment = Alignment.Center){
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "QuickCart Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(300.dp))
            Text(text = "QuickCart",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                fontSize = 35.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Shop Smart, Live Easy!",
                style = MaterialTheme.typography.bodyLarge
//                fontFamily = FontFamily.SansSerif,
//                fontWeight = FontWeight.Bold,
//                fontSize = 30.sp,
//                color = Color.White
            )
        }
    }
}
