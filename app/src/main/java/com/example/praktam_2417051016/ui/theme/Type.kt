package com.example.praktam_2417051016.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(

    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 30.sp
    ),

    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp
    ),

    titleSmall = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 18.sp
    ),

    bodyLarge = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp
    ),

    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),

    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),

    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )
)