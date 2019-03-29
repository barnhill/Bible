package com.pnuema.android.bible.statics;

import java.util.Locale;

public final class LanguageUtils {
    private static String ISO3_LANG = Locale.getDefault().getISO3Language().toLowerCase(Locale.getDefault());

    private LanguageUtils() {
    }

    public static String getISOLanguage() {
        return ISO3_LANG;
    }
}
