package com.falon.habit.presentation

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val lightColors = lightColors(
    primary = Color(Colors.AccentTeal),
    background = Color(Colors.LightBlueGrey),
    onPrimary = Color.White,
    onBackground = Color(Colors.TextBlack),
    surface = Color.White,
    onSurface = Color(Colors.TextBlack),
    error = Color(Colors.ErrorRed),
    onError = Color.White
)


val darkColors = darkColors(
    primary = Color(Colors.AccentTeal),
    background = Color(Colors.DarkGrey),
    onPrimary = Color.White,
    onBackground = Color.White,
    surface = Color(Colors.Grey),
    onSurface = Color.White,
    error = Color(Colors.ErrorRed),
    onError = Color.White
)
