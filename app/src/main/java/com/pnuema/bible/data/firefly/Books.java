package com.pnuema.bible.data.firefly;

import com.pnuema.bible.data.IBook;
import com.pnuema.bible.data.IBookProvider;

import java.util.List;

public class Books implements IBookProvider {
    private List<IBook> books;

    public Books(final List<IBook> books) {
        this.books = books;
    }

    @Override
    public List<IBook> getBooks() {
        return books;
    }
}
