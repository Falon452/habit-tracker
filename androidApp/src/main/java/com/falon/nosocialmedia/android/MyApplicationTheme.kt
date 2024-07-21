package com.falon.nosocialmedia.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
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
        lightColors(
            primary = darkColors.primary,
            background = darkColors.background,
            onPrimary = darkColors.onPrimary,
            onBackground = darkColors.onBackground,
            surface = darkColors.surface,
            onSurface = darkColors.onSurface,
        )
    } else {
        darkColors(
            primary = lightColors.primary,
            background = lightColors.background,
            onPrimary = lightColors.onPrimary,
            onBackground = lightColors.onBackground,
            surface = lightColors.surface,
            onSurface = lightColors.onSurface,
        )
    }
    val typography = Typography(
        body1 = TextStyle(
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
        colors = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
