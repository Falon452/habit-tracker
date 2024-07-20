package com.falon.nosocialmedia.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.falon.nosocialmedia.android.core.theme.darkColors
import com.falon.nosocialmedia.android.core.theme.lightColors

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = darkColors.primary,
            background = darkColors.background,
            onPrimary = darkColors.onPrimary,
            onBackground = darkColors.onBackground,
            surface = darkColors.surface,
            onSurface = darkColors.onSurface,
        )
    } else {
        lightColorScheme(
            primary = lightColors.primary,
            background = lightColors.background,
            onPrimary = lightColors.onPrimary,
            onBackground = lightColors.onBackground,
            surface = lightColors.surface,
            onSurface = lightColors.onSurface,
        )
    }
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
