package com.example.quickcart.data


import com.example.quickcart.model.Product
import com.example.quickcart.ui.theme.screens.productdetails.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartRepository {
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun addToCart(product: Product, onResult: (Boolean) -> Unit) {
        if (userId == null) {
            onResult(false)
            return
        }

        db.collection("users")
            .document(userId)
            .collection("cart")
            .document(product.id)
            .set(product)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}
fun getCartItems(onResult: (List<Product>) -> Unit) {
    if (userId == null) {
        onResult(emptyList())
        return
    }

    db.collection("users")
        .document(userId)
        .collection("cart")
        .get()
        .addOnSuccessListener { result ->
            val cartItems = result.mapNotNull { doc ->
                doc.toObject(Product::class.java).copy(id = doc.id)
            }
            onResult(cartItems)
        }
        .addOnFailureListener {
            onResult(emptyList())
        }
}

fun removeItem(productId: String, onComplete: (Boolean) -> Unit) {
    if (userId == null) {
        onComplete(false)
        return
    }

    db.collection("users")
        .document(userId)
        .collection("cart")
        .document(productId)
        .delete()
        .addOnSuccessListener { onComplete(true) }
        .addOnFailureListener { onComplete(false) }
}