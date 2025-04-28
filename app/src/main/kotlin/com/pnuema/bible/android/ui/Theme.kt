package com.pnuema.bible.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color(0xFFE8F5E9),
    secondary = Color(0xFF689F38),
    onSecondary = Color(0xFFE8F5E9),
    background = Color(0xFF1E1E1E),
    onBackground = Color(0xFFDEDEDE),
    surfaceVariant = Color(0xFF43A047),
    tertiary = Color(0xFFA0A0A0),
    error = Color(0xFFAA0000),

    primaryContainer = Color(0xFF43A047),
    onPrimaryContainer = Color(0xFFE8F5E9),
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF8BC34A),
    onPrimary = Color(0xFFFEFEFE),
    secondary = Color(0xFF689F38),
    onSecondary = Color(0xFFE8F5E9),
    background = Color(0xFFFCFEFD),
    onBackground = Color(0xFF212121),
    surfaceVariant = Color(0xFF689F38),
    tertiary = Color(0xFF797979),
    error = Color(0xFFCC0000),

    primaryContainer = Color(0xFF689F38),
    onPrimaryContainer = Color(0xFFFEFEFE),
)

private val Typography = Typography()

@Composable
fun BibleTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography,
        colorScheme = if (darkTheme) DarkColorPalette else LightColorPalette,
        content = content
    )
}