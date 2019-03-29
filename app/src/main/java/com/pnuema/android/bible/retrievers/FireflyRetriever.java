package com.pnuema.android.bible.retrievers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.pnuema.android.bible.data.firefly.Book;
import com.pnuema.android.bible.data.firefly.Books;
import com.pnuema.android.bible.data.firefly.ChapterCount;
import com.pnuema.android.bible.data.firefly.Verse;
import com.pnuema.android.bible.data.firefly.VerseCount;
import com.pnuema.android.bible.data.firefly.Verses;
import com.pnuema.android.bible.data.firefly.Version;
import com.pnuema.android.bible.data.firefly.Versions;
import com.pnuema.android.bible.retrofit.FireflyAPI;
import com.pnuema.android.bible.retrofit.IFireflyAPI;
import com.pnuema.android.bible.statics.App;
import com.pnuema.android.bible.statics.Constants;
import com.pnuema.android.bible.statics.CurrentSelected;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class FireflyRetriever extends BaseRetriever {
    @Override
    public void savePrefs() {
        CurrentSelected.savePref(Constants.KEY_SELECTED_VERSION + getTag(), new Gson().toJson(CurrentSelected.getVersion()));
        CurrentSelected.savePref(Constants.KEY_SELECTED_BOOK + getTag(), new Gson().toJson(CurrentSelected.getBook()));
        CurrentSelected.savePref(Constants.KEY_SELECTED_CHAPTER + getTag(), new Gson().toJson(CurrentSelected.getChapter()));
        CurrentSelected.savePref(Constants.KEY_SELECTED_VERSE + getTag(), new Gson().toJson(CurrentSelected.getVerse()));
    }

    @Override
    public void readPrefs() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext());

        CurrentSelected.setVersion(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSION + getTag(), ""), String.class));
        CurrentSelected.setBook(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_BOOK + getTag(), ""), Integer.class));
        CurrentSelected.setChapter(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_CHAPTER + getTag(), ""), Integer.class));
        CurrentSelected.setVerse(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSE + getTag(), ""), Integer.class));
    }

    @Override
    public void getVersions() {
        final IFireflyAPI api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI.class);
        final Call<List<Version>> call = api.getVersions(null); //TODO select language
        call.enqueue(new Callback<List<Version>>() {
            @Override
            public void onResponse(@NonNull final Call<List<Version>> call, @NonNull final Response<List<Version>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                final List<Version> versions = response.body();
                if (versions == null || versions.isEmpty()) {
                    return;
                }

                setChanged();
                notifyObservers(new Versions(new ArrayList<>(versions)));
            }

            @Override
            public void onFailure(@NonNull final Call<List<Version>> call, @NonNull final Throwable t) {
            }
        });
    }

    @Override
    public void getChapters(final String book) {
        final IFireflyAPI api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI.class);
        final Call<ChapterCount> call = api.getChapterCount(CurrentSelected.getBook(), CurrentSelected.getVersion());
        call.enqueue(new Callback<ChapterCount>() {
            @Override
            public void onResponse(@NonNull final Call<ChapterCount> call, @NonNull final Response<ChapterCount> response) {
                final ChapterCount chapterCount = response.body();

                if (chapterCount == null || !response.isSuccessful()) {
                    return;
                }

                setChanged();
                notifyObservers(chapterCount);
            }

            @Override
            public void onFailure(@NonNull final Call<ChapterCount> call, @NonNull final Throwable t) {
            }
        });
    }

    @Override
    public void getVerseCount(final String version, final String book, final String chapter) {
        final IFireflyAPI api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI.class);
        final Call<VerseCount> call = api.getVerseCount(book, chapter, version);
        call.enqueue(new Callback<VerseCount>() {
            @Override
            public void onResponse(@NonNull final Call<VerseCount> call, @NonNull final Response<VerseCount> response) {
                final VerseCount verseCount = response.body();
                if (verseCount == null) {
                    return;
                }

                setChanged();
                notifyObservers(verseCount);
            }

            @Override
            public void onFailure(@NonNull final Call<VerseCount> call, @NonNull final Throwable t) {
            }
        });
    }

    @Override
    public void getBooks() {
        final IFireflyAPI api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI.class);
        final Call<List<Book>> call = api.getBooks(CurrentSelected.getVersion());
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull final Call<List<Book>> call, @NonNull final Response<List<Book>> response) {
                final List<Book> books = response.body();
                if (books == null || books.isEmpty() || !response.isSuccessful()) {
                    return;
                }

                setChanged();
                notifyObservers(new Books(new ArrayList<>(books)));
            }

            @Override
            public void onFailure(@NonNull final Call<List<Book>> call, @NonNull final Throwable t) {
            }
        });
    }

    @Override
    public void getVerses(final String version, final String book, final String chapter) {
        final IFireflyAPI api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI.class);
        final Call<List<Verse>> call = api.getChapterVerses(book, chapter, version);
        call.enqueue(new Callback<List<Verse>>() {
            @Override
            public void onResponse(@NonNull final Call<List<Verse>> call, @NonNull final Response<List<Verse>> response) {
                final List<Verse> verses = response.body();
                if (verses == null) {
                    return;
                }

                setChanged();
                notifyObservers(new Verses(new ArrayList<>(verses)));
            }

            @Override
            public void onFailure(@NonNull final Call<List<Verse>> call, @NonNull final Throwable t) {
            }
        });
    }
}
