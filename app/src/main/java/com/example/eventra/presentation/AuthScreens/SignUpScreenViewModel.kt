package com.example.eventra.presentation.AuthScreens


import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class SignUpScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()



    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var rememberMe by mutableStateOf(false)
        private set
    var signupMessage by mutableStateOf("")
    var signupSuccess by mutableStateOf(false)

    var nameError by mutableStateOf(false)
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf(false)
    var confirmPasswordError by mutableStateOf("")

    var loginMessage by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)

    fun onNameChange(value: String) {
        name = value
        nameError = false
    }

    fun onEmailChange(value: String) {
        email = value
        emailError = ""
    }

    fun onPasswordChange(value: String) {
        password = value
        passwordError = false
        confirmPasswordError = ""
    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword = value
        confirmPasswordError = ""
    }

    fun onRememberMeChange(value: Boolean) {
        rememberMe = value
    }

    fun signUp(onResult:(Boolean,String)->Unit){
        var hasError = false

        // Name
        if (name.isEmpty()) {
            nameError = true
            hasError = true
        }

        // Email
        if (email.isEmpty()) {
            emailError = "* Required"
            hasError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Invalid email format"
            hasError = true
        } else {
            emailError = ""
        }

        // Password
        if (password.isEmpty()) {
            passwordError = true
            hasError = true
        }

        // Confirm Password
        if (confirmPassword.isEmpty()) {
            confirmPasswordError = "* Required"
            hasError = true
        } else if (password != confirmPassword) {
            confirmPasswordError = "Passwords do not match"
            hasError = true
        } else {
            confirmPasswordError = ""
        }

        // Agar koi error hai to signup stop
        if (hasError) return

        nameError = false
        emailError = ""
        passwordError = false
        confirmPasswordError = ""

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        // Save user info in 'users' collection
                        val user = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "createdAt" to System.currentTimeMillis()
                        )
                        db.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                signupMessage = "User added successfully!"
                                signupSuccess = true
                                onResult(true, signupMessage)
                            }
                            .addOnFailureListener { e ->
                                signupMessage = "Firestore error: ${e.message}"
                                signupSuccess = false
                                onResult(false, signupMessage)
                            }
                    } else {
                        signupMessage = "Error: UID is null"
                        signupSuccess = false
                        onResult(false, signupMessage)
                    }
                } else {
                    signupMessage = task.exception?.message ?: "Signup failed"
                    signupSuccess = false
                    onResult(false, signupMessage)
                }
            }
    }

    fun firebaseAuthWithGoogle(idToken: String, onResult: (Boolean, String) -> Unit) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    loginMessage = "Google Login Successful"
                    loginSuccess = true
                    onResult(true, loginMessage)
                } else {
                    loginMessage = task.exception?.message ?: "Google login failed"
                    loginSuccess = false
                    onResult(false, loginMessage)
                }

            }
    }
}