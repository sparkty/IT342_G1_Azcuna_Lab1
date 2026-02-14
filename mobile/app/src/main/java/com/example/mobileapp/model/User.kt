package com.example.mobileapp.model

data class User(
    val username: String,
    val email: String,
    val password: String? = null
)