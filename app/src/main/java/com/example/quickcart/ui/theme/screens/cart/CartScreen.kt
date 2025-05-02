package com.example.quickcart.ui.theme.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.quickcart.model.Product
import com.example.quickcart.repository.CartRepository

@Composable
fun CartScreen() {
    val cartRepo = remember { CartRepository() }
    var cartItems by remember { mutableStateOf<List<Product>>(emptyList()) }
    var totalPrice by remember { mutableStateOf(0.0) }

    fun loadCart() {
        cartRepo.getCartItems {
            cartItems = it
            totalPrice = it.sumOf { item -> item.price }
        }
    }

    LaunchedEffect(true) {
        loadCart()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Cart") })
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            if (cartItems.isEmpty()) {
                Text("Your cart is empty.")
                return@Column
            }

            LazyColumn {
                items(cartItems) { product ->
                    CartItemRow(product) {
                        cartRepo.removeItem(product.id) {
                            loadCart()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Total: $${"%.2f".format(totalPrice)}", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun CartItemRow(product: Product, onRemove: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {

        Row(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            Row {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 12.dp)
                )
                Column {
                    Text(product.name, style = MaterialTheme.typography.bodyLarge)
                    Text("$${product.price}", color = MaterialTheme.colorScheme.primary)
                }
            }

            Text(
                text = "Remove",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable { onRemove() }
            )
        }
    }
}