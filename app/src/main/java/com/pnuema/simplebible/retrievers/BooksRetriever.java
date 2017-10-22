package com.pnuema.simplebible.retrievers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.retrofit.API;
import com.pnuema.simplebible.retrofit.IAPI;
import com.pnuema.simplebible.statics.Constants;
import com.pnuema.simplebible.statics.CurrentSelected;

import java.util.Iterator;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksRetriever extends Observable {
    public void loadData(Context context) {
        IAPI api = API.getInstance(context).create(IAPI.class);
        Call<Books> call = api.getBooks(CurrentSelected.getVersion().id);
        call.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(@NonNull Call<Books> call, @NonNull Response<Books> response) {
                Books books = response.body();
                if (books == null || books.response == null || !response.isSuccessful()) {
                    return;
                }

                setChanged();

                //TODO skip this if preference is set to allow not just old and new testaments
                Iterator<Books.Book> iterator = books.response.books.iterator();
                while(iterator.hasNext()) {
                    Books.Book next = iterator.next();
                    if(!next.testament.equalsIgnoreCase(Constants.OLD_TESTAMENT_IDENT) && !next.testament.equalsIgnoreCase(Constants.NEW_TESTAMENT_IDENT)) {
                        iterator.remove();
                    }
                }

                notifyObservers(books);
            }

            @Override
            public void onFailure(@NonNull Call<Books> call, @NonNull Throwable t) {
            }
        });
    }
}
