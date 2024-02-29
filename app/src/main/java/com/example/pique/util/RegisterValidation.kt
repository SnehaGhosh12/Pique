package com.example.pique.util

sealed class RegisterValidation(){
    object Success: RegisterValidation()
    data class Failed(val message: String): RegisterValidation()
}

data class RegisteredFieldState(
    val email:RegisterValidation,
    val password: RegisterValidation
)
