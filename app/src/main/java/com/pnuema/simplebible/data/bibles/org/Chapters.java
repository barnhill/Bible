package com.pnuema.simplebible.data.bibles.org;

import java.io.Serializable;
import java.util.List;

public class Chapters implements Serializable {
    public ChaptersResponse response;

    public class ChaptersResponse implements Serializable {
        public List<Chapter> chapters;
        public Meta meta;
    }

    public class Chapter implements Serializable {
        public String chapter;
        public String auditid;
        public String label;
        public String id;
        public String osis_end;
        public Parent parent;
        public AdjacentChapter next;
        public AdjacentChapter previous;
        public String copyright;
    }

    public class Parent implements Serializable {
        public PathNameId book;
    }

    public class AdjacentChapter implements Serializable {
        public PathNameId chapter;
    }
}
