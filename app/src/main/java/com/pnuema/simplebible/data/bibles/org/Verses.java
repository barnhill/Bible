package com.pnuema.simplebible.data.bibles.org;

import com.pnuema.simplebible.data.IVerse;
import com.pnuema.simplebible.data.IVerseProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Verses implements IVerseProvider, Serializable {
    public VersesResponse response;

    @Override
    public List<IVerse> getVerses() {
        if (response == null) {
            return null;
        }

        List<IVerse> list = new ArrayList<>();
        list.addAll(response.verses);

        return list;
    }

    public class VersesResponse implements Serializable {
        public List<Verse> verses;
        public Meta meta;
    }

    public class Verse implements IVerse, Serializable {
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

        @Override
        public String getText() {
            return text;
        }
    }

    public class Parent implements Serializable {
        public PathNameId chapter;
    }

    public class AdjacentVerse implements Serializable {
        public PathNameId verse;
    }
}
