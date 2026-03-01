package com.example.praktam_2417051016.model

import androidx.annotation.DrawableRes

data class CoffeeShop(
    val name: String,
    val description: String,
    val price: String,
    @DrawableRes val imageRes: Int
)