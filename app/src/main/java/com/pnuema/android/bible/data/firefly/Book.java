package com.pnuema.android.bible.data.firefly;

import com.pnuema.android.bible.data.IBook;

@SuppressWarnings ("unused")
public class Book implements IBook {
    private int book_id;
    private String title;
    private boolean newTestament;

    @Override
    public int getId() {
        return book_id;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getAbbreviation() {
        return null; //TODO have it return the actual abbreviation from the API
    }

    public boolean isNewTestament() {
        return newTestament;
    }
}
