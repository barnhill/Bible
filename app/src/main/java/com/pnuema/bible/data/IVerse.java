package com.pnuema.bible.data;

import android.content.Context;

public interface IVerse {
    CharSequence getText(Context context);
    String getVerseNumber();
    String getCopyright();
}
