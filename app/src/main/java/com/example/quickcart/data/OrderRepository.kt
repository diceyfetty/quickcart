package com.example.quickcart.data

import com.example.quickcart.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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