package com.pnuema.simplebible.statics;

import com.pnuema.simplebible.retrievers.DBTRetriever;

public final class Constants {
    public static final String OLD_TESTAMENT_IDENT = "OT";
    public static final String NEW_TESTAMENT_IDENT = "NT";

    public static final String DEFAULT_RETRIEVER_TYPE = DBTRetriever.class.getName();
    public static final String KEY_RETRIEVER_TYPE = "KEY_RETRIEVER_TYPE";

    public static final String KEY_SELECTED_VERSION = "KEY_SELECTED_VERSION";
    public static final String KEY_SELECTED_BOOK = "KEY_SELECTED_BOOK";
    public static final String KEY_SELECTED_CHAPTER = "KEY_SELECTED_CHAPTER";
    public static final String KEY_SELECTED_VERSE = "KEY_SELECTED_VERSE";

    private Constants() {
    }
}
