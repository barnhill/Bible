package com.pnuema.bible.android.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val Typography = BibleTypography(
    title1 = bibleTextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Black,
        lineHeight = 36.sp
    ),
    title2 = bibleTextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    title3 = bibleTextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Black,
        lineHeight = 30.sp
    ),
    title4 = bibleTextStyle(
        fontSize = 22.sp,
        lineHeight = 30.sp
    ),
    title5 = bibleTextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Black,
        lineHeight = 26.sp
    ),
    title6 = bibleTextStyle(
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    body1 = bibleTextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    body2 = bibleTextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Black,
        lineHeight = 24.sp
    ),
    caption1 = bibleTextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    caption2 = bibleTextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Black,
        lineHeight = 20.sp
    ),
    subCaption1 = bibleTextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    subCaption2 = bibleTextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Black,
        lineHeight = 16.sp
    ),
    footnote1 = bibleTextStyle(
        fontSize = 10.sp,
        lineHeight = 16.sp
    ),
    footnote2 = bibleTextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Black,
        lineHeight = 16.sp
    )
)

private fun bibleTextStyle(
    fontSize: TextUnit,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit
) = TextStyle(
    color = Color.Black,
    fontSize = fontSize,
    fontWeight = fontWeight,
    fontFamily = FontFamily.SansSerif,
    lineHeight = lineHeight,
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)

fun TextStyle.italicize() = this.copy(
    fontStyle = FontStyle.Italic
)

fun TextStyle.strikeThrough() = this.copy(
    textDecoration = TextDecoration.LineThrough
)

fun TextStyle.underline() = this.copy(
    textDecoration = TextDecoration.Underline
)