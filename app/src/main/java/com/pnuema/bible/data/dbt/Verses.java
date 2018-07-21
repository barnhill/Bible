package com.pnuema.bible.data.dbt;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spanned;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IVerse;
import com.pnuema.bible.data.IVerseProvider;
import com.pnuema.bible.statics.HtmlUtils;

import java.io.Serializable;
import java.util.List;

public class Verses implements IVerseProvider, Serializable {
    private List<IVerse> verses;

    public Verses(List<IVerse> verses) {
        this.verses = verses;
    }

    @Override
    public List<IVerse> getVerses() {
        return verses;
    }

    public class Verse implements IVerse, Serializable {
        public String book_name;
        public String book_id;
        public String book_order;
        public String chapter_id;
        public String chapter_title;
        public String verse_id;
        public String verse_text;
        public String paragraph_number;

        @Override
        public Spanned getText(Context context) {
            return HtmlUtils.fromHtml(htmlFormatting(context));
        }

        @Override
        public String getVerseNumber() {
            return verse_id;
        }

        @Override
        public String getCopyright() {
            return null;
        }

        private String htmlFormatting(Context context) {
            //remove paragraph tags
            String formatted = verse_text.replaceAll("\n\t\t\t", "");

            //verse number
            formatted = "<font color=\"#" + String.format("#%06x", ContextCompat.getColor(context, R.color.secondary_text) & 0xffffff) + "\"><small>" + getVerseNumber() + "&nbsp;&nbsp;</small></font>" + formatted;

            return formatted;
        }
    }
}
