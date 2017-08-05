package com.pnuema.simplebible.statics;

import java.util.Locale;

public final class LanguageUtils {
    private static String ISO3_LANG = Locale.getDefault().getISO3Language().toLowerCase(Locale.getDefault());

    public static String getISOLanguage() {
        return ISO3_LANG;
    }
}
