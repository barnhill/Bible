package com.pnuema.bible.retrievers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.pnuema.bible.data.IBook;
import com.pnuema.bible.data.IChapter;
import com.pnuema.bible.data.bibles.org.Books;
import com.pnuema.bible.data.bibles.org.Chapter;
import com.pnuema.bible.data.bibles.org.Verses;
import com.pnuema.bible.data.bibles.org.Versions;
import com.pnuema.bible.retrofit.BiblesOrgAPI;
import com.pnuema.bible.retrofit.IBiblesOrgAPI;
import com.pnuema.bible.statics.App;
import com.pnuema.bible.statics.Constants;
import com.pnuema.bible.statics.CurrentSelected;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retriever for bibles.org
 */
public final class BiblesOrgRetriever extends BaseRetriever {
    @Override
    public void savePrefs() {
        CurrentSelected.savePref(Constants.KEY_SELECTED_VERSION + getTag(), new Gson().toJson(CurrentSelected.getVersion()));
        CurrentSelected.savePref(Constants.KEY_SELECTED_BOOK + getTag(), new Gson().toJson(CurrentSelected.getBook()));
        CurrentSelected.savePref(Constants.KEY_SELECTED_CHAPTER + getTag(), new Gson().toJson(CurrentSelected.getChapter()));
        CurrentSelected.savePref(Constants.KEY_SELECTED_VERSE + getTag(), new Gson().toJson(CurrentSelected.getVerse()));
    }

    @Override
    public void readPrefs() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext());

        CurrentSelected.setVersion(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSION + getTag(), ""), Versions.Version.class));
        CurrentSelected.setBook(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_BOOK + getTag(), ""), Books.Book.class));
        CurrentSelected.setChapter(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_CHAPTER + getTag(), ""), Chapter.class));
        CurrentSelected.setVerse(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSE + getTag(), ""), Verses.Verse.class));
    }

    @Override
    public void getVersions() {
        IBiblesOrgAPI api = BiblesOrgAPI.getInstance(App.getContext()).create(IBiblesOrgAPI.class);
        Call<Versions> call = api.getVersions();
        call.enqueue(new Callback<Versions>() {
            @Override
            public void onResponse(@NonNull Call<Versions> call, @NonNull Response<Versions> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Versions versions = response.body();
                if (versions == null || versions.response == null) {
                    return;
                }

                setChanged();
                notifyObservers(versions);
            }

            @Override
            public void onFailure(@NonNull Call<Versions> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void getChapters(String book) {
        IBiblesOrgAPI api = BiblesOrgAPI.getInstance(App.getContext()).create(IBiblesOrgAPI.class);
        Call<Books> call = api.getBooks(CurrentSelected.getVersion().getId()); //books call gets the chapters too and caches them so that two calls arent necessary from bibles.org
        call.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(@NonNull Call<Books> call, @NonNull Response<Books> response) {
                Books books = response.body();

                if (books == null || !response.isSuccessful()) {
                    return;
                }

                IBook targetBook = null;
                for (IBook listBook : books.getBooks()) {
                     if (listBook.getId().equalsIgnoreCase(book)) {
                         targetBook = listBook;
                         break;
                     }
                }

                if (targetBook == null || targetBook.getChapters() == null || targetBook.getChapters().isEmpty()) {
                    return;
                }

                List<IChapter> chapters = targetBook.getChapters();
                if (chapters == null) {
                    return;
                }

                setChanged();
                notifyObservers(chapters);
            }

            @Override
            public void onFailure(@NonNull Call<Books> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void getBooks() {
        IBiblesOrgAPI api = BiblesOrgAPI.getInstance(App.getContext()).create(IBiblesOrgAPI.class);
        Call<Books> call = api.getBooks(CurrentSelected.getVersion().getId());
        call.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(@NonNull Call<Books> call, @NonNull Response<Books> response) {
                Books books = response.body();
                if (books == null || books.response == null || !response.isSuccessful()) {
                    return;
                }

                //TODO skip this if preference is set to allow not just old and new testaments
                Iterator<Books.Book> iterator = books.response.books.iterator();
                while(iterator.hasNext()) {
                    Books.Book next = iterator.next();
                    if(!next.testament.equalsIgnoreCase(Constants.OLD_TESTAMENT_IDENT) && !next.testament.equalsIgnoreCase(Constants.NEW_TESTAMENT_IDENT)) {
                        iterator.remove();
                    }
                }

                setChanged();
                notifyObservers(books);
            }

            @Override
            public void onFailure(@NonNull Call<Books> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void getVerses(String version, String book, String chapter) {
        IBiblesOrgAPI api = BiblesOrgAPI.getInstance(App.getContext()).create(IBiblesOrgAPI.class);
        Call<Verses> call = api.getVerses(version, book, chapter);
        call.enqueue(new Callback<Verses>() {
            @Override
            public void onResponse(@NonNull Call<Verses> call, @NonNull Response<Verses> response) {
                Verses verses = response.body();
                if (verses == null || verses.response == null) {
                    return;
                }

                setChanged();
                notifyObservers(verses);
            }

            @Override
            public void onFailure(@NonNull Call<Verses> call, @NonNull Throwable t) {
            }
        });
    }
}
