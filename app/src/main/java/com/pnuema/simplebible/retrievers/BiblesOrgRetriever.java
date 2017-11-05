package com.pnuema.simplebible.retrievers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pnuema.simplebible.data.bibles.org.Books;
import com.pnuema.simplebible.data.bibles.org.Chapters;
import com.pnuema.simplebible.data.bibles.org.Verses;
import com.pnuema.simplebible.data.bibles.org.Versions;
import com.pnuema.simplebible.retrofit.BiblesOrgAPI;
import com.pnuema.simplebible.retrofit.IBiblesOrgAPI;
import com.pnuema.simplebible.statics.Constants;
import com.pnuema.simplebible.statics.CurrentSelected;

import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class BiblesOrgRetriever extends BaseRetreiver {
    @Override
    public void getVersions(Context context) {
        IBiblesOrgAPI api = BiblesOrgAPI.getInstance(context).create(IBiblesOrgAPI.class);
        Call<Versions> call = api.getVersions();
        call.enqueue(new Callback<Versions>() {
            @Override
            public void onResponse(@NonNull Call<Versions> call, @NonNull Response<Versions> response) {
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
    public void getChapters(Context context, String book) {
        IBiblesOrgAPI api = BiblesOrgAPI.getInstance(context).create(IBiblesOrgAPI.class);
        Call<Chapters> call = api.getChapters(book);
        call.enqueue(new Callback<Chapters>() {
            @Override
            public void onResponse(@NonNull Call<Chapters> call, @NonNull Response<Chapters> response) {
                Chapters chapters = response.body();
                if (chapters == null || chapters.response == null || !response.isSuccessful()) {
                    return;
                }

                setChanged();
                notifyObservers(chapters);
            }

            @Override
            public void onFailure(@NonNull Call<Chapters> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void getBooks(Context context) {
        IBiblesOrgAPI api = BiblesOrgAPI.getInstance(context).create(IBiblesOrgAPI.class);
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
    public void getVerses(Context context, String version, String book, String chapter) {
        IBiblesOrgAPI api = BiblesOrgAPI.getInstance(context).create(IBiblesOrgAPI.class);
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
