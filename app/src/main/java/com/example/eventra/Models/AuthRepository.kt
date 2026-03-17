package com.example.eventra.Models.AuthRepository

interface AuthRepository {
    // Sign in with email & password
    suspend fun signIn(email: String, password: String): Result<Unit>

    // Sign up with name, email & password
    suspend fun signUp(name: String, email: String, password: String): Result<Unit>

    // Sign in with Google
    suspend fun signInWithGoogle(idToken: String): Result<Unit>

    // Sign in with Facebook
    suspend fun signInWithFacebook(token: String): Result<Unit>

    // Reset password
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun logOut()
}