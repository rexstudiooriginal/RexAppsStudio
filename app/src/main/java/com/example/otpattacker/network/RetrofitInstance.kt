package com.example.otpattacker.network

import com.example.otpattacker.data.OtpApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://xdotpapi-39c44db71f37.herokuapp.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: OtpApiService by lazy {
        retrofit.create(OtpApiService::class.java)
    }
}