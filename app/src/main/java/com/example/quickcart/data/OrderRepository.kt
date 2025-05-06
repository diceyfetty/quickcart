package com.example.quickcart.data

import com.example.quickcart.ui.theme.screens.chekout.UserOrder
import com.example.quickcart.ui.theme.screens.productdetails.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.v1.StructuredQuery.Order
import java.util.*

class OrderRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid

    fun placeOrder(cartItems: List<Product>, onComplete: (Boolean) -> Unit) {
        if (userId == null || cartItems.isEmpty()) {
            onComplete(false)
            return
        }

        val orderData = mapOf(
            "items" to cartItems,
            "timestamp" to Date(),
            "totalPrice" to cartItems.sumOf { it.price }
        )

        db.collection("users")
            .document(userId)
            .collection("orders")
            .add(orderData)
            .addOnSuccessListener {
                clearCart(onComplete)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
    fun getOrders(onResult: (List<UserOrder>) -> Unit) {
        if (userId == null) {
            onResult(emptyList())
            return
        }

        db.collection("users")
            .document(userId)
            .collection("orders")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val orders = result.mapNotNull { doc ->
                    doc.toObject(UserOrder::class.java)
                }
                onResult(orders)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    private fun clearCart(onComplete: (Boolean) -> Unit) {
        db.collection("users")
            .document(userId!!)
            .collection("cart")
            .get()
            .addOnSuccessListener { result ->
                val batch = db.batch()
                for (doc in result.documents) {
                    batch.delete(doc.reference)
                }
                batch.commit()
                    .addOnSuccessListener { onComplete(true) }
                    .addOnFailureListener { onComplete(false) }
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
}