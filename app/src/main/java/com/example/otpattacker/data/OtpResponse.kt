package com.example.otpattacker.data

data class OtpResponse(
    val success: Boolean,
    val message: String? = null,
    val target: String? = null,
    val status: String? = null,
    val error: String? = null
)