package com.pnuema.bible.data.bibles.org;

import com.pnuema.bible.data.IChapter;
import com.pnuema.bible.data.IChapterProvider;

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
        List<Chapter> chapters;
        Meta meta;
    }

    public class Parent implements Serializable {
        public PathNameId book;
    }
}
