package com.pnuema.simplebible.data;

import java.util.List;

public interface IBook {
    String getId();
    String getName();
    String getAbbreviation();
    List<IChapter> getChapters();
}
