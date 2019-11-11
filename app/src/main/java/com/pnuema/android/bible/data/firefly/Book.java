package com.pnuema.android.bible.data.firefly;

import com.pnuema.android.bible.data.IBook;

import androidx.annotation.NonNull;

@SuppressWarnings ("unused")
public class Book implements IBook {
    private int book_id;
    private String title;
    private boolean newTestament;

    @Override
    public int getId() {
        return book_id;
    }

    @NonNull
    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getAbbreviation() {
        return null; //TODO if API is modified to return abbreviation this is here to support that
    }

    public boolean isNewTestament() {
        return newTestament;
    }
}
