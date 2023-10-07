package com.pnuema.bible.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val _darkColorPalette = darkColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color(0xFFE8F5E9),
    background = Color(0xFF1E1E1E),
    onBackground = Color(0xFFDEDEDE),
    surfaceVariant = Color(0xFF43A047),
    secondary = Color(0xFFA0A0A0),
)

private val _lightColorPalette = lightColorScheme(
    primary = Color(0xFF8BC34A),
    onPrimary = Color(0xFFFEFEFE),
    background = Color(0xFFFCFEFD),
    onBackground = Color(0xFF212121),
    surfaceVariant = Color(0xFF689F38),
    secondary = Color(0xFF797979),
)

private val _typography = Typography()

@Composable
fun BibleTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        typography = _typography,
        colorScheme = if (darkTheme) _darkColorPalette else _lightColorPalette,
        content = content
    )
}