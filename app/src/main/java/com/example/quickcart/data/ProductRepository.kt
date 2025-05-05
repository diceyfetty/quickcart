package com.example.quickcart.data

import com.example.quickcart.ui.theme.screens.productdetails.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()

    fun fetchProducts(onResult: (List<Product>) -> Unit) {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val productList = result.map { doc ->
                    val product = doc.toObject<Product>()
                    product.copy(id = doc.id)
                }
                onResult(productList)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}