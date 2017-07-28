package com.pnuema.simplebible.data;

import java.util.List;

public class Verses {
    public VersesResponse response;

    public class VersesResponse {
        public List<Verse> verses;
        public Meta meta;
    }

    public class Verse {
        public String auditid;
        public String verse;
        public String lastverse;
        public String id;
        public String osis_end;
        public String label;
        public String reference;
        public String prev_osis_id;
        public String next_osis_id;
        public String text;
        public Parent parent;
        public AdjacentVerse next;
        public AdjacentVerse previous;
        public String copyright;
    }

    public class Parent {
        public PathNameId chapter;
    }

    public class AdjacentVerse {
        public PathNameId verse;
    }
}
