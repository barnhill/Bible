package com.pnuema.simplebible.data;

import java.util.List;

public class Books {
    public BooksResponse response;

    public class BooksResponse {
        public List<Book> books;
        public Meta meta;
    }

    public class Book {
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
    }

    public class Parent {
        public PathNameId version;
    }

    public class AdjacentBook {
        public PathNameId book;
    }
}
