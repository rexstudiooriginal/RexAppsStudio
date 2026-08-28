package com.example.otpattacker.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OtpApiService {
    @GET("/api/{phone}")
    suspend fun sendOtp(
        @Path("phone") phone: String,
        @Query("mode") mode: String,
        @Query("type") type: String = "otp"
    ): OtpResponse
}