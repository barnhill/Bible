package com.pnuema.bible.data.dbt;

import com.pnuema.bible.data.IChapter;

public final class Chapter implements IChapter {
    private String id;
    private String name;

    public Chapter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
