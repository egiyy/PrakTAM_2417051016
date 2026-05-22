package com.example.praktam_2417051016.data.api

import com.example.praktam_2417051016.data.model.CoffeeShop
import retrofit2.http.GET

interface ApiService {
    @GET("coffeeshop_menu.json")
    suspend fun getCoffees(): List<CoffeeShop>
}