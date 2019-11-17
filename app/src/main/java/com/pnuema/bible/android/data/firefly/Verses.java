package com.pnuema.bible.android.data.firefly;

import com.pnuema.bible.android.data.IVerse;
import com.pnuema.bible.android.data.IVerseProvider;

import java.util.List;

import androidx.annotation.NonNull;

public class Verses implements IVerseProvider {
    private List<IVerse> verses;

    public Verses(final List<IVerse> verses) {
        this.verses = verses;
    }

    @NonNull
    public List<IVerse> getVerses() {
        return verses;
    }
}
