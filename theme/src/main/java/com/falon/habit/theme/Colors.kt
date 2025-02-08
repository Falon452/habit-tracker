package com.falon.habit.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkGrey = Color(0xFF121212)
val ErrorRed = Color(0xFFF44336)
val LifeGrey = Color(0xFF363636)
val LifeDarkCream = Color(0xFFF7C59F)
val LifeLightCream = Color(0xFFEFEFD0)
val LifeBlue = Color(0xFF00A7E1)

internal val DarkColorScheme = darkColorScheme(
    primary = LifeBlue,
    onPrimary = LifeLightCream,
    secondary = LifeGrey,
    onSecondary = LifeLightCream,
    tertiary = Color(0xFF03DAC6),
    background = DarkGrey,
    onBackground = LifeLightCream,
    surface = LifeGrey,
    onSurface = LifeLightCream,
    surfaceVariant = LifeDarkCream,
    error = ErrorRed,
    onError = LifeLightCream
)

internal val LightColorScheme = lightColorScheme(
    primary = LifeBlue,
    onPrimary = Color.White,
    secondary = LifeGrey,
    onSecondary = LifeLightCream,
    tertiary = Color(0xFF018786),
    background = LifeLightCream,
    onBackground = LifeGrey,
    surface = LifeDarkCream,
    onSurface = LifeGrey,
    surfaceVariant = LifeGrey,
    error = ErrorRed,
    onError = Color.White
)
