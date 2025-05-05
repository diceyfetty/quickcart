package com.example.quickcart.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quickcart.SplashScreen
import com.example.quickcart.ui.theme.screens.cart.CartScreen
import com.example.quickcart.ui.theme.screens.dashboard.HomeScreen
import com.example.quickcart.ui.theme.screens.login.LoginScreen
import com.example.quickcart.ui.theme.screens.onboarding.OnboardingScreen
import com.example.quickcart.ui.theme.screens.chekout.OrderHistoryScreen
import com.example.quickcart.ui.theme.screens.productdetails.Product
import com.example.quickcart.ui.theme.screens.productdetails.ProductDetailScreen
import com.example.quickcart.ui.theme.screens.register.RegisterScreen
import com.example.quickcart.ui.theme.screens.userprofile.ProfileScreen

object Routes{
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val HOME = "home"
    const val REGISTER = "register"
}
@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.SPLASH){
        composable (Routes.SPLASH) {
            SplashScreen (navController)}
        composable(Routes.LOGIN)
        { LoginScreen(navController) }
        composable(Routes.HOME)
        { HomeScreen(navController) }
        composable(Routes.ONBOARDING)
        { OnboardingScreen(navController) }
        composable(Routes.REGISTER)
        { RegisterScreen(navController) }
        composable("cart") {
            CartScreen()
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("orders") {
            OrderHistoryScreen()
        }
        composable("product_detail") { backStackEntry ->
            val product = navController.previousBackStackEntry
                ?.savedStateHandle?.get<Product>("selected_product")
            if (product != null) {
                ProductDetailScreen(navController, product)
            }
        }
    }
}