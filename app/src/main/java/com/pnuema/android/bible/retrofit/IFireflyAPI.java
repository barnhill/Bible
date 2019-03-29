package com.pnuema.android.bible.retrofit;

import com.pnuema.android.bible.data.firefly.Book;
import com.pnuema.android.bible.data.firefly.ChapterCount;
import com.pnuema.android.bible.data.firefly.Verse;
import com.pnuema.android.bible.data.firefly.VerseCount;
import com.pnuema.android.bible.data.firefly.Version;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IFireflyAPI {
    @GET("versions")
    Call<List<Version>> getVersions(@Nullable @Query("lang") String language);

    @GET("books")
    Call<List<Book>> getBooks(@Nullable @Query("v") String version);

    @GET("books/{book}/chapters")
    Call<ChapterCount> getChapterCount(@Path("book") Integer book, @Nullable @Query ("v") String version);

    @GET("books/{book}/chapters/{chapter}/verses")
    Call<VerseCount> getVerseCount(@Path("book") String book, @Path("chapter") String chapter, @Nullable @Query("v") String version);

    @GET("books/{book}/chapters/{chapter}")
    Call<List<Verse>> getChapterVerses(@Path("book") String book, @Path("chapter") String chapter, @Nullable @Query("v") String version);
}
