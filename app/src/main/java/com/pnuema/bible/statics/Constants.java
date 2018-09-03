package com.pnuema.bible.statics;

import com.pnuema.bible.retrievers.FireflyRetriever;

public final class Constants {
    public static final String DEFAULT_RETRIEVER_TYPE = FireflyRetriever.class.getName();
    public static final String KEY_RETRIEVER_TYPE = "KEY_RETRIEVER_TYPE";

    public static final String KEY_SELECTED_VERSION = "KEY_SELECTED_VERSION";
    public static final String KEY_SELECTED_BOOK = "KEY_SELECTED_BOOK";
    public static final String KEY_SELECTED_CHAPTER = "KEY_SELECTED_CHAPTER";
    public static final String KEY_SELECTED_VERSE = "KEY_SELECTED_VERSE";

    private Constants() {
    }
}
