package com.pnuema.simplebible.data.bibles.org;

import com.pnuema.simplebible.data.IBook;
import com.pnuema.simplebible.data.IBookProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Books implements IBookProvider, Serializable {
    public BooksResponse response;

    @Override
    public List<IBook> getBooks() {
        if (response == null) {
            return null;
        }

        List<IBook> list = new ArrayList<>();
        list.addAll(response.books);

        return list;
    }

    public class BooksResponse implements Serializable {
        public List<Book> books;
        public Meta meta;
    }

    public class Book implements IBook, Serializable {
        public String version_id;
        public String name;
        public String abbr;
        public String ord;
        public String book_group_id;
        public String testament;
        public String id;
        public String osis_end;
        public Parent parent;
        public AdjacentBook next;
        public AdjacentBook previous;
        public String copyright;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getAbbreviation() {
            return abbr;
        }
    }

    public class Parent implements Serializable {
        public PathNameId version;
    }

    public class AdjacentBook implements Serializable {
        public PathNameId book;
    }
}
