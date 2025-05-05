package com.example.quickcart.ui.theme.screens.chekout

import com.example.quickcart.ui.theme.screens.productdetails.Product
import java.util.*

data class Order(
    val items: List<Product> = emptyList(),
    val timestamp: Date = Date(),
    val totalPrice: Double = 0.0
)
