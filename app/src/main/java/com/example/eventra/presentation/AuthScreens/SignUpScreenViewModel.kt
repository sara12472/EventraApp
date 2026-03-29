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
class SignUpScreenViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

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

    fun signUp(onResult: (Boolean, String) -> Unit) {

        var hasError = false

        if (name.isEmpty()) {
            nameError = true
            hasError = true
        }

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

        if (confirmPassword != password) {
            confirmPasswordError = "Passwords do not match"
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {

            val result = repository.signUp(
                name = name,
                email = email,
                password = password
            )

            if (result.isSuccess) {
                signupSuccess = true
                signupMessage = "Signup successful"
                onResult(true, signupMessage)
            } else {
                signupSuccess = false
                signupMessage =
                    result.exceptionOrNull()?.message ?: "Signup failed"
                onResult(false, signupMessage)
            }

        }
    }



}