package com.example.quickcart.ui.theme.screens.onboarding

import androidx.annotation.DrawableRes
import com.example.quickcart.R

data class OnboardingPage(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)

val onboardingPages = listOf(
    OnboardingPage(
        title = "Welcome to QuickCart",
        description = "Shop your favorite items with ease and speed.",
        imageRes = R.drawable.cart
    ),
    OnboardingPage(
        title = "Track Your Orders",
        description = "Real-time order tracking with instant updates.",
        imageRes = R.drawable.tracking
    ),
    OnboardingPage(
        title = "Secure Payments",
        description = "Your transactions are safe and encrypted.",
        imageRes = R.drawable.payment
    )
)