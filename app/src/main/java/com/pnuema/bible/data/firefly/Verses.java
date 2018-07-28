package com.pnuema.bible.data.firefly;

import java.util.List;

public class Verses {
    private List<Verse> verses;

    public Verses(final List<Verse> verses) {
        this.verses = verses;
    }

    public List<Verse> getVerses() {
        return verses;
    }
}
