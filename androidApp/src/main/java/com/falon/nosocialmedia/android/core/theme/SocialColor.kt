package com.falon.nosocialmedia.android.core.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.falon.nosocialmedia.core.presentation.model.Colors

val AccentViolet = Color(Colors.AccentViolet)
val LightBlueGrey = Color(Colors.LightBlueGrey)
val TextBlack = Color(Colors.TextBlack)
val DarkGrey = Color(Colors.DarkGrey)
val Grey = Color(Colors.Grey)

val lightColors = lightColors(
    primary = AccentViolet,
    background = LightBlueGrey,
    onPrimary = Color.White,
    onBackground = TextBlack,
    surface = Color.White,
    onSurface = TextBlack,
)

val darkColors = darkColors(
    primary = AccentViolet,
    background = DarkGrey,
    onPrimary = Color.White,
    onBackground = Color.White,
    surface = Grey,
    onSurface = Color.White
)
