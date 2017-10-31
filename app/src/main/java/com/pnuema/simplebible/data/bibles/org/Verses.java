package com.pnuema.simplebible.data.bibles.org;

import java.io.Serializable;
import java.util.List;

public class Verses implements Serializable {
    public VersesResponse response;

    public class VersesResponse implements Serializable {
        public List<Verse> verses;
        public Meta meta;
    }

    public class Verse implements Serializable {
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

    public class Parent implements Serializable {
        public PathNameId chapter;
    }

    public class AdjacentVerse implements Serializable {
        public PathNameId verse;
    }
}
