package com.example.quickcart.data

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository: ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser: FirebaseUser?
        = FirebaseAuth.getInstance().currentUser
    fun registerUser(email:String, password:String, onResult:(Boolean, String?) -> Unit){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task
            ->
            if (task.isSuccessful) {
                onResult(true, null)
            }else {onResult(false, task.exception?.message)
        }
    }
}
    fun LoginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit){
    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {task ->
            if (task.isSuccessful){
                onResult(true, null)
            }else{onResult(false, task.exception?.message)
        }
    }
}
    fun logoutUser() {
    firebaseAuth.signOut()
    }
}