package com.example.praktam_2417051016.model

import com.example.praktam_2417051016.R

object Menu {
    val coffeeList = listOf(
        CoffeeShop(
            name = "Americano",
            description = "Kopi robusta dengan aroma khas.",
            size = "Large, Black",
            price = "Rp25.000",
            imageRes = R.drawable.americano
        ),
        CoffeeShop(
            name = "Butterscotch",
            description = "Rasa manis creamy dari campuran gula cokelat dan mentega.",
            size = "Small, Choco Chip",
            price = "Rp30.000",
            imageRes = R.drawable.butterscotch
        ),
        CoffeeShop(
            name = "Caramel Latte",
            description = "Minuman kopi espresso dengan susu dan sirup karamel.",
            size = "Large, Oat Milk",
            price = "Rp30.000",
            imageRes = R.drawable.caramel_latte
        ),
        CoffeeShop(
            name = "Vanila Latte",
            description = "Minuman kopi espresso dengan susu dan sirup vanilla.",
            size = "Large, Oat Milk",
            price = "Rp28.000",
            imageRes = R.drawable.vanila_latte
        ),
        CoffeeShop(
            name = "Chocolate",
            description = "Minuman cokelat manis dengan rasa lembut dan creamy.",
            size = "Medium, Full Cream",
            price = "Rp32.000",
            imageRes = R.drawable.chocolate
        ),
        CoffeeShop(
            name = "Matcha",
            description = "Minuman teh hijau bubuk khas Jepang.",
            size = "Large, Oat Milk",
            price = "Rp32.000",
            imageRes = R.drawable.matcha
        )
    )
}