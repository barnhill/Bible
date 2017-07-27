package com.pnuema.simplebible.data;

import java.util.List;

public class Chapters {
    public ChaptersResponse response;

    public class ChaptersResponse {
        public List<Chapter> chapters;
        public Meta meta;
    }

    public class Chapter {
        public String auditid;
        public String label;
        public String id;
        public String osis_end;
        public Parent parent;
        public AdjacentChapter next;
        public AdjacentChapter previous;
        public String copyright;
    }

    public class Parent {
        public PathNameId book;
    }

    public class AdjacentChapter {
        public PathNameId chapter;
    }
}
