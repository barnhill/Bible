package com.pnuema.simplebible.data;

import android.content.Context;

public interface IVerse {
    CharSequence getText(Context context);
    String getVerseNumber();
    String getCopyright();
}
