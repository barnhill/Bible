package com.pnuema.simplebible.retrofit;

import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.data.Verses;
import com.pnuema.simplebible.data.Versions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAPI {
    @GET("versions.js")
    Call<Versions> getVersions();

    @GET("versions/{version}/books.js?include_chapters=false")
    Call<Books> getBooks(@Path("version") String version);

    @GET("versions/{version}/books.js?include_chapters=false")
    Call<Chapters> getChapters(@Path("version") String version);

    @GET("chapters/{version}:{book}.{chapter}/verses.js")
    Call<Verses> getVerses(@Path("version") String version, @Path("book") String book, @Path("chapter") String chapter);
}
