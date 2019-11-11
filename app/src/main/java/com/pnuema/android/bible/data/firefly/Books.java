package com.pnuema.android.bible.data.firefly;

import com.pnuema.android.bible.data.IBook;
import com.pnuema.android.bible.data.IBookProvider;

import java.util.List;

import androidx.annotation.NonNull;

public class Books implements IBookProvider {
    private List<IBook> books;

    public Books(final List<IBook> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public List<IBook> getBooks() {
        return books;
    }
}
