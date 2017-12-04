package com.pnuema.simplebible.retrievers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pnuema.simplebible.data.dbt.Books;
import com.pnuema.simplebible.data.dbt.Verses;
import com.pnuema.simplebible.data.dbt.Versions;
import com.pnuema.simplebible.data.dbt.Volume;
import com.pnuema.simplebible.retrofit.DBTAPI;
import com.pnuema.simplebible.retrofit.IDBTAPI;
import com.pnuema.simplebible.statics.CurrentSelected;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retriever for dbt.io
 */
public final class DBTRetriever extends BaseRetriever {
    @Override
    public void getVersions(Context context) {
        IDBTAPI api = DBTAPI.getInstance(context).create(IDBTAPI.class);
        Call<List<Volume>> call = api.getVersions(DBTAPI.getApiKey(), Locale.getDefault().getISO3Language());

        call.enqueue(new Callback<List<Volume>>() {
            @Override
            public void onResponse(@NonNull Call<List<Volume>> call, @NonNull Response<List<Volume>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<Volume> volumes = response.body();
                if (volumes == null || volumes.isEmpty()) {
                    return;
                }

                setChanged();
                notifyObservers(new Versions(volumes));
            }

            @Override
            public void onFailure(@NonNull Call<List<Volume>> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void getChapters(Context context, String bookId) {
        IDBTAPI api = DBTAPI.getInstance(context).create(IDBTAPI.class);

        //setup for old testament retrieval
        String damid = CurrentSelected.getVersion().getId().substring(0, 6) + "O1" + CurrentSelected.getVersion().getId().substring(8);

        Call<List<Books.Book>> callot = api.getBooks(DBTAPI.getApiKey(), damid);
        callot.enqueue(new Callback<List<Books.Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Books.Book>> call, @NonNull Response<List<Books.Book>> response) {
                List<Books.Book> books = response.body();
                if (!response.isSuccessful() || books == null) {
                    return;
                }

                List<Books.Book> bookList = new ArrayList<>(books);

                //setup for new testament retrieval
                String damid2 = CurrentSelected.getVersion().getId().substring(0, 6) + "N1" + CurrentSelected.getVersion().getId().substring(8);
                Call<List<Books.Book>> NTCall = api.getBooks(DBTAPI.getApiKey(), damid2);
                NTCall.enqueue(new Callback<List<Books.Book>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Books.Book>> call, @NonNull Response<List<Books.Book>> response) {
                        List<Books.Book> booksNT = response.body();
                        if (!response.isSuccessful() || booksNT == null) {
                            //NT failure
                            for (Books.Book book : books) {
                                if (book.getId().equalsIgnoreCase(bookId)) {
                                    setChanged();
                                    notifyObservers(book);
                                    break;
                                }
                            }
                            return;
                        }

                        //merge OT with NT
                        bookList.addAll(booksNT);

                        for (Books.Book book : bookList) {
                            if (book.getId().equalsIgnoreCase(bookId)) {
                                setChanged();
                                notifyObservers(book);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Books.Book>> call, @NonNull Throwable t) {
                    }
                });
            }

            @Override
            public void onFailure (@NonNull Call < List < Books.Book >> call, @NonNull Throwable t){
            }
        });
    }

    @Override
    public void getBooks(Context context) {
        IDBTAPI api = DBTAPI.getInstance(context).create(IDBTAPI.class);

        //setup for old testament retrieval
        String damid = CurrentSelected.getVersion().getId().substring(0, 6) + "O1" + CurrentSelected.getVersion().getId().substring(8);

        Call<List<Books.Book>> callot = api.getBooks(DBTAPI.getApiKey(), damid);
        callot.enqueue(new Callback<List<Books.Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Books.Book>> call, @NonNull Response<List<Books.Book>> response) {
                List<Books.Book> books = response.body();
                if (!response.isSuccessful() || books == null) {
                    return;
                }

                List<Books.Book> bookList = new ArrayList<>(books);

                //setup for new testament retrieval
                String damid2 = CurrentSelected.getVersion().getId().substring(0, 6) + "N1" + CurrentSelected.getVersion().getId().substring(8);
                Call<List<Books.Book>> NTCall = api.getBooks(DBTAPI.getApiKey(), damid2);
                NTCall.enqueue(new Callback<List<Books.Book>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Books.Book>> call, @NonNull Response<List<Books.Book>> response) {
                        List<Books.Book> booksNT = response.body();
                        if (!response.isSuccessful() || booksNT == null) {
                            //NT failure
                            setChanged();
                            notifyObservers(new Books(new ArrayList<>(bookList)));
                            return;
                        }

                        //merge OT with NT
                        bookList.addAll(booksNT);

                        setChanged();
                        notifyObservers(new Books(new ArrayList<>(bookList)));
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Books.Book>> call, @NonNull Throwable t) {
                    }
                });
            }

            @Override
            public void onFailure (@NonNull Call < List < Books.Book >> call, @NonNull Throwable t){
            }
        });
    }

    @Override
    public void getVerses(Context context, String damId, String book, String chapter) {
        IDBTAPI api = DBTAPI.getInstance(context).create(IDBTAPI.class);
        Call<List<Verses.Verse>> call = api.getVerses(DBTAPI.getApiKey(), ((Books.Book)CurrentSelected.getBook()).getDamId(), book, chapter);
        call.enqueue(new Callback<List<Verses.Verse>>() {
            @Override
            public void onResponse(@NonNull Call<List<Verses.Verse>> call, @NonNull Response<List<Verses.Verse>> response) {
                List<Verses.Verse> verses = response.body();
                if (!response.isSuccessful() || verses == null) {
                    return;
                }

                setChanged();
                notifyObservers(new Verses(new ArrayList<>(verses)));
            }

            @Override
            public void onFailure(@NonNull Call<List<Verses.Verse>> call, @NonNull Throwable t) {
            }
        });
    }
}
