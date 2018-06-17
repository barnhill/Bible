package com.pnuema.simplebible.retrievers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.pnuema.simplebible.data.dbt.Books;
import com.pnuema.simplebible.data.dbt.Chapter;
import com.pnuema.simplebible.data.dbt.Verses;
import com.pnuema.simplebible.data.dbt.Versions;
import com.pnuema.simplebible.data.dbt.Volume;
import com.pnuema.simplebible.retrofit.DBTAPI;
import com.pnuema.simplebible.retrofit.IDBTAPI;
import com.pnuema.simplebible.statics.App;
import com.pnuema.simplebible.statics.Constants;
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
    private static final String DEFAULT_DAM_ID = "ENGKJVO2ET";
    private static final String OT_SUFFIX = "O2";
    private static final String NT_SUFFIX = "N2";

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

        CurrentSelected.setVersion(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSION + getTag(), "{\"abbreviation\":\"KJV\",\"display\":\"King James Version\",\"id\":\"ENGKJVN1ET\",\"language\":\"eng\"}"), Versions.Version.class));
        CurrentSelected.setBook(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_BOOK + getTag(), "{\"book_id\":\"Gen\",\"book_name\":\"Genesis\",\"book_order\":\"1\",\"chapters\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50\",\"dam_id\":\"ENGKJVO2ET\",\"number_of_chapters\":\"50\"}"), Books.Book.class));
        CurrentSelected.setChapter(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_CHAPTER + getTag(), "{\"id\":\"1\",\"name\":\"1\"}"), Chapter.class));
        CurrentSelected.setVerse(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSE + getTag(), "{\"book_id\":\"Gen\",\"book_name\":\"Genesis\",\"book_order\":\"1\",\"chapter_id\":\"1\",\"chapter_title\":\"Chapter 1\",\"paragraph_number\":\"1\",\"verse_id\":\"1\",\"verse_text\":\"In the beginning God created the heaven and the earth.\"}"), Verses.Verse.class));
    }

    @Override
    public void getVersions() {
        IDBTAPI api = DBTAPI.getInstance(App.getContext()).create(IDBTAPI.class);
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
    public void getChapters(String bookId) {
        IDBTAPI api = DBTAPI.getInstance(App.getContext()).create(IDBTAPI.class);

        //setup for old testament retrieval
        String fullDamId = CurrentSelected.getVersion().getId() == null ? DEFAULT_DAM_ID : CurrentSelected.getVersion().getId();
        String damid = fullDamId.substring(0, 6) + OT_SUFFIX + fullDamId.substring(8);

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
                String damid2 = fullDamId.substring(0, 6) + NT_SUFFIX + fullDamId.substring(8);
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
    public void getBooks() {
        IDBTAPI api = DBTAPI.getInstance(App.getContext()).create(IDBTAPI.class);

        //setup for old testament retrieval
        String fullDamId = CurrentSelected.getVersion().getId() == null ? DEFAULT_DAM_ID : CurrentSelected.getVersion().getId();
        String damid = fullDamId.substring(0, 6) + OT_SUFFIX + fullDamId.substring(8);

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
                String damid2 = fullDamId.substring(0, 6) + NT_SUFFIX + fullDamId.substring(8);
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
    public void getVerses(String damId, String book, String chapter) {
        IDBTAPI api = DBTAPI.getInstance(App.getContext()).create(IDBTAPI.class);

        //setup for old testament retrieval
        String fullDamId = CurrentSelected.getVersion().getId() == null ? DEFAULT_DAM_ID : CurrentSelected.getVersion().getId();
        String damid = fullDamId.substring(0, 6) + OT_SUFFIX + fullDamId.substring(8);

        Call<List<Verses.Verse>> callot = api.getVerses(DBTAPI.getApiKey(), damid, book, chapter);
        callot.enqueue(new Callback<List<Verses.Verse>>() {
            @Override
            public void onResponse(@NonNull Call<List<Verses.Verse>> call, @NonNull Response<List<Verses.Verse>> response) {
                List<Verses.Verse> verses = response.body();
                if (!response.isSuccessful() || verses == null) {
                    return;
                }

                if (!verses.isEmpty()) {
                    setChanged();
                    notifyObservers(new Verses(new ArrayList<>(verses)));
                    return;
                }

                //setup for new testament retrieval
                String damid2 = fullDamId.substring(0, 6) + NT_SUFFIX + fullDamId.substring(8);

                Call<List<Verses.Verse>> callot = api.getVerses(DBTAPI.getApiKey(), damid2, book, chapter);
                callot.enqueue(new Callback<List<Verses.Verse>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Verses.Verse>> call, @NonNull Response<List<Verses.Verse>> response) {
                        List<Verses.Verse> versesNT = response.body();
                        if (!response.isSuccessful() || versesNT == null) {
                            //NT failure
                            setChanged();
                            notifyObservers(null);
                            return;
                        }

                        setChanged();
                        notifyObservers(new Verses(new ArrayList<>(versesNT)));
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Verses.Verse>> call, @NonNull Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<Verses.Verse>> call, @NonNull Throwable t) {
            }
        });
    }
}
