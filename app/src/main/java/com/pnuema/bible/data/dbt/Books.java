package com.pnuema.bible.data.dbt;

import com.pnuema.bible.data.IBook;
import com.pnuema.bible.data.IBookProvider;
import com.pnuema.bible.data.IChapter;
import com.pnuema.bible.data.IChapterProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Books implements IBookProvider {
    private List<IBook> books;

    public Books(List<IBook> books) {
        this.books = books;
    }

    @Override
    public List<IBook> getBooks() {
        return books;
    }

    public final class Book implements IBook, IChapterProvider {
        private String dam_id;
        private String book_id;
        private String book_name;
        private String book_order;
        private String number_of_chapters;
        private String chapters;

        public String getDamId() {
            return dam_id;
        }

        @Override
        public String getId() {
            return book_id;
        }

        @Override
        public String getName() {
            return book_name;
        }

        @Override
        public String getAbbreviation() {
            return book_id;
        }

        @Override
        public List<IChapter> getChapters() {
            List<IChapter> chapterList = new ArrayList<>();

            List<String> chaps = Arrays.asList(chapters.split("\\s*,\\s*"));

            for (String chap : chaps) {
                chapterList.add(new Chapter(chap, chap));
            }

            return chapterList;
        }
    }
}