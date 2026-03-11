package com.example.eventra.presentation.AuthScreens

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var rememberMe by mutableStateOf(false)
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf(false)

    var loginMessage by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailError = ""
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordError = false
    }

    fun onRememberMeChange(value: Boolean) {
        rememberMe = value
    }
    fun signIn(onResult: (Boolean, String) -> Unit) {

        var hasError = false

        if (email.isEmpty()) {
            emailError = "* Required"
            hasError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Invalid email format"
            hasError = true
        }

        if (password.isEmpty()) {
            passwordError = true
            hasError = true
        }

        if (hasError) return

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    loginMessage = "Login successful"
                    loginSuccess = true
                    onResult(true, loginMessage)
                } else {
                    loginMessage = task.exception?.message ?: "Login failed"
                    loginSuccess = false
                    onResult(false, loginMessage)
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