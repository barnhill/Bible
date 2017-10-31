package com.pnuema.simplebible.retrievers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pnuema.simplebible.data.bibles.org.Versions;
import com.pnuema.simplebible.retrofit.API;
import com.pnuema.simplebible.retrofit.IAPI;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionsRetriever extends Observable {
    public void loadData(Context context) {
        IAPI api = API.getInstance(context).create(IAPI.class);
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
}
