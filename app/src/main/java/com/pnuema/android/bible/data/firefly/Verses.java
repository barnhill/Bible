package com.pnuema.android.bible.data.firefly;

import com.pnuema.android.bible.data.IVerse;
import com.pnuema.android.bible.data.IVerseProvider;

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
