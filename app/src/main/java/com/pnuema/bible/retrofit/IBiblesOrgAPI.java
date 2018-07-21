package com.pnuema.bible.retrofit;

import com.pnuema.bible.data.bibles.org.Books;
import com.pnuema.bible.data.bibles.org.Verses;
import com.pnuema.bible.data.bibles.org.Versions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IBiblesOrgAPI {
    @GET("versions.js")
    Call<Versions> getVersions();

    @GET("versions/{version}/books.js?include_chapters=true")
    Call<Books> getBooks(@Path("version") String version);

    @GET("chapters/{version}:{book}.{chapter}/verses.js")
    Call<Verses> getVerses(@Path("version") String version, @Path("book") String book, @Path("chapter") String chapter);
}
