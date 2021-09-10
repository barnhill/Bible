package com.pnuema.bible.android.statics

import android.content.Intent
import android.text.TextUtils
import kotlinx.coroutines.runBlocking
import java.util.*

object DeepLinks {
    fun handleDeepLinks(intent: Intent) {
        val data = intent.data ?: return

        if (TextUtils.isEmpty(data.getQueryParameter("book")) && TextUtils.isEmpty(data.getQueryParameter("bookid"))
                || TextUtils.isEmpty(data.getQueryParameter("chapter"))
                || TextUtils.isEmpty(data.getQueryParameter("verse"))) {
            return
        }

        //val bookAbb = data.getQueryParameter("book")
        //TODO look up book if it was provided to get the book id
        val bookid = Integer.valueOf(Objects.requireNonNull<String>(data.getQueryParameter("bookid")))
        val chapter = Integer.valueOf(Objects.requireNonNull<String>(data.getQueryParameter("chapter")))
        val verse = Integer.valueOf(Objects.requireNonNull<String>(data.getQueryParameter("verse")))

        CurrentSelected.book = bookid
        CurrentSelected.chapter = chapter
        CurrentSelected.verse = verse

        runBlocking {
            CurrentSelected.savePreferences()
        }
    }
}
