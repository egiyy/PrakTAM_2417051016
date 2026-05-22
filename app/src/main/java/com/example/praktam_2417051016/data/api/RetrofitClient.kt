package com.example.praktam_2417051016.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL =
        "https://gist.githubusercontent.com/egiyy/412aae1a7fa0af39c90062045c87d49e/raw/0adc4e50141cf111f8fd35d26fc2d10855354f7b/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}