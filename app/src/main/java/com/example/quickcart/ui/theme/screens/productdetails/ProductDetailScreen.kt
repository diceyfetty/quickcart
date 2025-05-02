package com.example.quickcart.ui.theme.screens.productdetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
//import com.example.quickcart.model.Product
import com.example.quickcart.repository.CartRepository

@Composable
fun ProductDetailScreen(
    navController: NavController,
    product: Product
) {
    val cartRepo = remember { CartRepository() }
    var addedMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {

        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(product.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Price: $${product.price}", color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(product.description)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                cartRepo.addToCart(product) { success ->
                    addedMessage = if (success) "Added to cart!" else "Failed to add"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add to Cart")
        }

        if (addedMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(addedMessage, color = MaterialTheme.colorScheme.secondary)
        }
    }
}