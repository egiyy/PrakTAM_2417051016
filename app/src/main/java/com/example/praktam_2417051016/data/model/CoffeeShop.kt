package com.example.praktam_2417051016.data.model

import com.google.gson.annotations.SerializedName

data class CoffeeShop(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("size")
    val size: String = "",

    @SerializedName("price")
    val price: String,

    @SerializedName("image_url")
    val imageUrl: Any,           // Mendukung String (URL) atau Int (R.drawable)

    val isFavorite: Boolean = false
)