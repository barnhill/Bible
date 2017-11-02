package com.pnuema.simplebible.data.bibles.org;

import com.pnuema.simplebible.data.IChapter;
import com.pnuema.simplebible.data.IChapterProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chapters implements IChapterProvider, Serializable {
    public ChaptersResponse response;

    @Override
    public List<IChapter> getChapters() {
        if (response == null) {
            return null;
        }

        List<IChapter> list = new ArrayList<>();
        list.addAll(response.chapters);

        return list;
    }

    public class ChaptersResponse implements Serializable {
        public List<Chapter> chapters;
        public Meta meta;
    }

    public class Chapter implements IChapter, Serializable {
        public String chapter;
        public String auditid;
        public String label;
        public String id;
        public String osis_end;
        public Parent parent;
        public AdjacentChapter next;
        public AdjacentChapter previous;
        public String copyright;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return chapter;
        }
    }

    public class Parent implements Serializable {
        public PathNameId book;
    }

    public class AdjacentChapter implements Serializable {
        public PathNameId chapter;
    }
}
