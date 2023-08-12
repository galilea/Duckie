package com.example.duckie

import kotlinx.serialization.Serializable

@Serializable
data class Duckie(
    val message: String,
    val url: String
)
