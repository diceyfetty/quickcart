package com.example.quickcart.data

import com.google.firebase.auth.FirebaseAuth

class UserRepository {
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }

    fun logout(onComplete: () -> Unit) {
        auth.signOut()
        onComplete()
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
