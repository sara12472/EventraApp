package com.example.eventra.presentation.AuthScreens


import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventra.Models.AuthRepository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var rememberMe by mutableStateOf(false)
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf("")

    var loginMessage by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)


    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailError = ""
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordError = ""
    }

    fun onRememberMeChange(value: Boolean) {
        rememberMe = value
    }
    fun signIn(onResult: (Boolean, String) -> Unit) {
        // clear previous errors
        emailError = ""
        passwordError = ""

        var hasError = false

        if (email.isEmpty()) {
            emailError = "* Required"
            hasError = true
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Invalid email format"
            hasError = true
        }

        if (password.isEmpty()) {
            passwordError = "* Required"
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {

            val result = repository.signIn(email, password)

            if (result.isSuccess) {

                loginSuccess = true
                loginMessage = "Login successful"
                onResult(true, loginMessage)

            } else {

                loginSuccess = false

                val message = result.exceptionOrNull()?.message ?: ""

                when {

                    message.contains("no user", true) ||
                            message.contains("not found", true) ||
                            message.contains("record", true) -> {

                        emailError = "Email not found"
                        onResult(false, "Email not found")
                    }

                    message.contains("password", true) -> {

                        passwordError = "Wrong password"
                        onResult(false, "Wrong password")
                    }

                    else -> {

                        passwordError = "Login failed"
                        onResult(false, "Login failed")
                    }
                }
            }
        }
    }

    fun signInWithGoogle(idToken: String, onResult: (Boolean, String) -> Unit) {

            viewModelScope.launch {

                val result = repository.signInWithGoogle(idToken)

                if (result.isSuccess) {
                    loginSuccess = true
                    loginMessage = "Google Login Successful"
                    onResult(true, loginMessage)
                } else {
                    loginSuccess = false
                    loginMessage = result.exceptionOrNull()?.message ?: "Google login failed"
                    onResult(false, loginMessage)
                }

            }

        }
    fun resetPassword(email: String, onResult: (String) -> Unit) {
        if (email.isEmpty()) {
            onResult("Email is required")
            return
        }

        viewModelScope.launch {
            val result = repository.resetPassword(email)
            if (result.isSuccess) {
                onResult("Reset email sent successfully")
            } else {
                onResult(result.exceptionOrNull()?.message ?: "Reset failed")
            }
        }
    }
    fun logOut(){
        viewModelScope.launch {
            repository.logOut()
        }
    }



    }
