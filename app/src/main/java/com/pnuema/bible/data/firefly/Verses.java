package com.pnuema.bible.data.firefly;

import com.pnuema.bible.data.IVerse;
import com.pnuema.bible.data.IVerseProvider;

import java.util.List;

public class Verses implements IVerseProvider {
    private List<IVerse> verses;

    public Verses(final List<IVerse> verses) {
        this.verses = verses;
    }

    public List<IVerse> getVerses() {
        return verses;
    }
}
