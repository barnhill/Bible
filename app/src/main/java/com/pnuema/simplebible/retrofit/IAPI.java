package com.pnuema.simplebible.retrofit;

import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.data.Versions;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IAPI {
    @GET("versions.js")
    Call<Versions> getVersions();

    @GET("versions/eng-KJV/books.js?include_chapters=false")
    Call<Books> getBooks();

    @GET("versions/eng-KJV/books.js?include_chapters=false")
    Call<Chapters> getChapters();
}
