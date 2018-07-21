package com.pnuema.bible.retrofit;

import com.pnuema.bible.data.dbt.Books;
import com.pnuema.bible.data.dbt.Verses;
import com.pnuema.bible.data.dbt.Volume;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDBTAPI {
    @GET("library/volume?media=text&delivery=mobile&status=live&v=2")
    Call<List<Volume>> getVersions(@Query("key") String apiKey, @Query("language_family_code") String language);

    @GET("library/book?v=2")
    Call<List<Books.Book>> getBooks(@Query("key") String apiKey, @Query("dam_id") String damId);

    @GET("text/verse?v=2")
    Call<List<Verses.Verse>> getVerses(@Query("key") String apiKey, @Query("dam_id") String damId, @Query("book_id") String bookId, @Query("chapter_id") String chapterId);
}
