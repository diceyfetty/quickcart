package com.example.quickcart.ui.theme.screens.chekout

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quickcart.data.OrderRepository
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen() {
    val orderRepo = remember { OrderRepository() }
    var orders by remember { mutableStateOf<List<UserOrder>>(emptyList()) }

    LaunchedEffect(true) {
        orderRepo.getOrders {
            orders = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Order History") })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            if (orders.isEmpty()) {
                item {
                    Text("No orders found.")
                }
            }

            items(orders) { order ->
                OrderCard(order)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun OrderCard(order: UserOrder) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Order Date: ${dateFormat.format(order.timestamp)}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            order.items.forEach {
                Text("- ${it.name}  |  $${it.price}")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Total: $${"%.2f".format(order.totalPrice)}", fontWeight = FontWeight.Bold)
        }
    }
}