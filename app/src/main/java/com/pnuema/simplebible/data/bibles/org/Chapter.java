package com.pnuema.simplebible.data.bibles.org;

import com.pnuema.simplebible.data.IChapter;

import java.io.Serializable;

public class Chapter implements IChapter, Serializable {
    public String chapter;
    public String auditid;
    public String label;
    public String id;
    public String osis_end;
    public Chapters.Parent parent;
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
