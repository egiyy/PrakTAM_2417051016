package com.example.praktam_2417051016.data.repository

import com.example.praktam_2417051016.data.api.RetrofitClient
import com.example.praktam_2417051016.data.model.CoffeeShop

class CoffeeRepository {
    suspend fun getCoffees(): List<CoffeeShop> {
        return try {
            RetrofitClient.instance.getCoffees()
        } catch (e: Exception) {
            emptyList()
        }
    }
}