package com.pnuema.simplebible.data;

import java.util.List;

public class Books {
    public class Response {
        public List<Book> books;
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
        public ParentNameId version;
    }

    public class ParentNameId {
        public String path;
        public String name;
        public String id;
    }

    public class AdjacentBook {
        public ParentNameId book;
    }
}
