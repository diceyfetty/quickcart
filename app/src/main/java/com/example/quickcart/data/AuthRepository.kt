package com.example.quickcart.data

class AuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser:FirebaseAuthUser?
        get() = FirebaseAuth.currentUser
    fun registerUser(email:String, password:String, onResult:(Boolean, String?) -> Unit){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task
            ->
            if (task.isSuccessful) {
                onResult(true, null)
            }else {onResult(false, task.expectation?.message)
        }
    }
}
    fun LoginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit){
    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {task ->
            if (task.isSuccessfull){
                onResult(true, null)
            }else{onResult(false, task.expectation?.message)
        }
    }
}
    fun logoutUser() {
    firebaseAuth.signOut()
    }
}