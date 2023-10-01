package com.pnuema.bible.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.pnuema.bible.android.ui.BibleTheme.LocalBibleTypography

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

@Composable
fun BibleTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalBibleTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) _darkColorPalette else _lightColorPalette,
            content = content
        )
    }
}

object BibleTheme {
    val LocalBibleTypography = staticCompositionLocalOf {
        BibleTypography()
    }

    val typography: BibleTypography
        @Composable
        get() = LocalBibleTypography.current
}

@Immutable
data class BibleTypography(
    /**
     * XXL900
     */
    val title1: TextStyle = TextStyle.Default,
    /**
     * XXL400
     */
    val title2: TextStyle = TextStyle.Default,
    /**
     * XL900
     */
    val title3: TextStyle = TextStyle.Default,
    /**
     * XL400
     */
    val title4: TextStyle = TextStyle.Default,
    /**
     * Large900
     */
    val title5: TextStyle = TextStyle.Default,
    /**
     * Large400
     */
    val title6: TextStyle = TextStyle.Default,
    /**
     * Base400
     */
    val body1: TextStyle = TextStyle.Default,
    /**
     * Base800
     */
    val body2: TextStyle = TextStyle.Default,
    /**
     * Medium400
     */
    val caption1: TextStyle = TextStyle.Default,
    /**
     * Medium800
     */
    val caption2: TextStyle = TextStyle.Default,
    /**
     * Small400
     */
    val subCaption1: TextStyle = TextStyle.Default,
    /**
     * Small800
     */
    val subCaption2: TextStyle = TextStyle.Default,
    /**
     * XS400
     */
    val footnote1: TextStyle = TextStyle.Default,
    /**
     * XS800
     */
    val footnote2: TextStyle = TextStyle.Default
)
