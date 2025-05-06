package com.example.quickcart.ui.theme.screens.dashboard

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quickcart.R
import com.example.quickcart.data.AuthRepository
import com.example.quickcart.ui.theme.screens.productdetails.Product
import com.example.quickcart.data.ProductRepository
import com.example.quickcart.navigation.Routes

data class ProductCategory(val name: String, val imageRes: Int)

val sampleCategories = listOf(
    ProductCategory("Electronics", R.drawable.electronics),
    ProductCategory("Fashion", R.drawable.fashion),
    ProductCategory("Groceries", R.drawable.grocery),
    ProductCategory("Books", R.drawable.books),
    ProductCategory("Home", R.drawable.home_goods)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QuickCart") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF00BFA5),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
                    }

                    TextButton(onClick = {
                        AuthRepository().logoutUser()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }) {
                        Text("Logout", color = Color.White)
                    }
                }
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Welcome Back!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Browse by category", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow {
                items(sampleCategories) { category ->
                    CategoryCard(category)
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: ProductCategory) {
    Card(
        modifier = Modifier
            .padding(end = 12.dp)
            .size(width = 140.dp, height = 160.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(category.name, fontWeight = FontWeight.SemiBold)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val repo = remember { ProductRepository() }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(true) {
        repo.fetchProducts {
            products = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QuickCart") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF00BFA5), titleContentColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Featured Products", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                items(products) { product ->
                    ProductCard(product)
                }
            }, modifier = Modifier.fillMaxHeight())
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.name, fontWeight = FontWeight.SemiBold)
            Text("$${product.price}", color = Color(0xFF00BFA5))
        }
    }
}