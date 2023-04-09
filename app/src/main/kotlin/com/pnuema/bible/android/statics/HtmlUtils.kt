package com.pnuema.bible.android.statics

import android.text.Html
import android.text.Spanned

fun String.fromHtml(): Spanned {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
}
