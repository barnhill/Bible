package com.pnuema.android.bible.statics;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public final class DeepLinks {
    public static void handleDeepLinks(Intent intent) {
        Uri data = intent.getData();

        if (data == null
                || (TextUtils.isEmpty(data.getQueryParameter("book")) && TextUtils.isEmpty(data.getQueryParameter("bookid")))
                || TextUtils.isEmpty(data.getQueryParameter("chapter"))
                || TextUtils.isEmpty(data.getQueryParameter("verse"))) {
            return;
        }

        String bookAbb = data.getQueryParameter("book");
        //TODO look up book if it was provided to get the book id
        Integer bookid = Integer.valueOf(data.getQueryParameter("bookid"));
        Integer chapter = Integer.valueOf(data.getQueryParameter("chapter"));
        Integer verse = Integer.valueOf(data.getQueryParameter("verse"));

        CurrentSelected.setBook(bookid);
        CurrentSelected.setChapter(chapter);
        CurrentSelected.setVerse(verse);
        CurrentSelected.savePreferences();
    }
}
