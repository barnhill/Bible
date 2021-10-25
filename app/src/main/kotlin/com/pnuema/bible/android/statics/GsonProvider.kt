package com.pnuema.bible.android.statics

import com.google.gson.Gson

object GsonProvider {
    private val gson: Gson = Gson()
    fun get(): Gson = gson
}