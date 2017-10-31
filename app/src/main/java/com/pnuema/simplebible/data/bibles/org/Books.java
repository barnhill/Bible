package com.pnuema.simplebible.data.bibles.org;

import java.io.Serializable;
import java.util.List;

public class Books implements Serializable {
    public BooksResponse response;

    public class BooksResponse implements Serializable {
        public List<Book> books;
        public Meta meta;
    }

    public class Book implements Serializable {
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

    public class Parent implements Serializable {
        public PathNameId version;
    }

    public class AdjacentBook implements Serializable {
        public PathNameId book;
    }
}
