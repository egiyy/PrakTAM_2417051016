package com.example.praktam_2417051016.model

import com.example.praktam_2417051016.R

object Menu {

    val coffeeList = listOf(
        CoffeeShop(
            name = "Coffe",
            description = "Kopi hitam pekat dengan rasa kuat dan aroma khas.",
            price = "Rp20.000",
            imageRes = R.drawable.coffe
        ),
        CoffeeShop(
            name = "Matcha Latte",
            description = "Matcha Khas jepang dan creammy.",
            price = "Rp 25.000",
            imageRes = R.drawable.matcha
        ),
        CoffeeShop(
            name = "Bluebarry Milkshake",
            description = "Milkshake yang enak.",
            price = "Rp 30.000",
            imageRes = R.drawable.milksake
        )
    )
}