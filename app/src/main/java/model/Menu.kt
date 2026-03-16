package com.example.praktam_2417051016.model

import com.example.praktam_2417051016.R

object Menu {
    val coffeeList = listOf(
        CoffeeShop(
            name = "Americano",
            description = "Kopi robusta dengan aroma khas.",
            price = "Rp25.000",
            imageRes = R.drawable.americano
        ),
        CoffeeShop(
            name = "Butterscotch",
            description = "Rasa manis creamy dari campuran gula cokelat dan mentega, dengan aroma khas.",
            price = "Rp30.000",
            imageRes = R.drawable.butterscotch
        ),
        CoffeeShop(
            name = "Caramel Latte",
            description = "Minuman kopi espresso dengan susu dan sirup karamel.",
            price = "Rp30.000",
            imageRes = R.drawable.caramel_latte
        ),
        CoffeeShop(
            name = "Vanila Latte",
            description = "Minuman kopi espresso dengan susu dan sirup vanilla.",
            price = "Rp28.000",
            imageRes = R.drawable.vanila_latte
        ),
        CoffeeShop(
            name = "Chocolate",
            description = "Minuman cokelat manis dengan rasa lembut dan creamy.",
            price = "Rp32.000",
            imageRes = R.drawable.chocolate
        ),
        CoffeeShop(
            name = "Matcha",
            description = "Minuman teh hijau bubuk khas Jepang dengan rasa lembut dan sedikit pahit.",
            price = "Rp32.000",
            imageRes = R.drawable.matcha
        )
    )
}