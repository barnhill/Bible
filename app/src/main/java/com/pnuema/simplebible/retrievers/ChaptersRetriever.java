package com.pnuema.simplebible.retrievers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.retrofit.API;
import com.pnuema.simplebible.retrofit.IAPI;
import com.pnuema.simplebible.statics.Constants;

import java.util.Iterator;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChaptersRetriever extends Observable {
    public void loadData(Context context, String book) {
        IAPI api = API.getInstance(context).create(IAPI.class);
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
}
