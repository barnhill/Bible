package com.pnuema.simplebible.retrofit;

import com.pnuema.simplebible.data.Versions;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IAPI {
    @GET("versions.js")
    Call<Versions> getVersions();
}
