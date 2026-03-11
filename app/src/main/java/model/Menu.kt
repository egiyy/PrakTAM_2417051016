package com.example.praktam_2417051016.model

import com.example.praktam_2417051016.R

object Menu {

    val coffeeList = listOf(

        CoffeeShop(
            name = "Kopi Gula Aren",
            description = "Kopi gula aren pekat dengan aroma khas.",
            price = "Rp25.000",
            imageRes = R.drawable.coffe
        ),

        CoffeeShop(
            name = "Matcha Latte",
            description = "Minuman matcha khas Jepang yang creamy.",
            price = "Rp30.000",
            imageRes = R.drawable.matcha
        ),

        CoffeeShop(
            name = "Blueberry Milkshake",
            description = "Milkshake blueberry segar dan manis.",
            price = "Rp30.000",
            imageRes = R.drawable.milksake
        ),

        CoffeeShop(
            name = "Caramel Latte",
            description = "Perpaduan espresso, foam susu dan caremel.",
            price = "Rp28.000",
            imageRes = R.drawable.caramel_latte
        ),

        CoffeeShop(
            name = "Starawberry Farappuccino",
            description = "Minuman segar dengan rasa stroberi manis yang creamy.",
            price = "Rp32.000",
            imageRes = R.drawable.strawberry_frappuccino
        )

    )
}