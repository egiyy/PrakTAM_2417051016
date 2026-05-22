package com.example.praktam_2417051016.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CoffeeColorScheme = lightColorScheme(
    primary          = CoffeePrimary,
    secondary        = CoffeeSecondary,
    tertiary         = CoffeeTertiary,
    background       = CoffeeBackground,
    surface          = CoffeeSurface,
    onPrimary        = OnCoffeePrimary,
    onBackground     = OnCoffeeBackground,
    onSurface        = OnCoffeeSurface,
)

@Composable
fun PrakTAM_2417051016Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CoffeeColorScheme,
        typography  = AppTypography,
        content     = content
    )
}