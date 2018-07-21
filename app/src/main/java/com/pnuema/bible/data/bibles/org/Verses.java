package com.pnuema.bible.data.bibles.org;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spanned;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IVerse;
import com.pnuema.bible.data.IVerseProvider;
import com.pnuema.bible.statics.HtmlUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verses implements IVerseProvider, Serializable {
    public VersesResponse response;
    private static Pattern patternTitle = Pattern.compile("<h\\d\\b[^>]*>(.*?)<\\/h\\d>");

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
        public Spanned getText(Context context) {
            return HtmlUtils.fromHtml(htmlFormatting(context));
        }

        @Override
        public String getVerseNumber() {
            return verse;
        }

        @Override
        public String getCopyright() {
            return copyright;
        }

        private String htmlFormatting(Context context) {
            //remove paragraph tags
            String formatted = text.replaceAll("<p class=\"p\">", "").replaceAll("</p>", "");

            //verse number
            formatted = formatted.replaceAll("(<sup)\\s[a-zA-Z]+.+(sup>)", "<font color=\"#" + String.format("#%06x", ContextCompat.getColor(context, R.color.secondary_text) & 0xffffff) + "\"><small>" + getVerseNumber() + "&nbsp;&nbsp;</small></font>");

            //titles
            Matcher matcher = patternTitle.matcher(formatted);
            if (matcher.find()) {
                for (int i = 0; i <= matcher.groupCount(); i++) {
                    formatted = formatted.replaceFirst(patternTitle.pattern(), "<br/><font color=\"" + String.format("#%06x", ContextCompat.getColor(context, R.color.primary_text) & 0xffffff) + "\"><b>" +  matcher.group(i) + "</b></font><br/>");
                }
            }

            return formatted;
        }
    }

    public class Parent implements Serializable {
        public PathNameId chapter;
    }

    public class AdjacentVerse implements Serializable {
        public PathNameId verse;
    }
}
