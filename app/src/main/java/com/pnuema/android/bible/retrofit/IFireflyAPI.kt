package com.pnuema.android.bible.retrofit

import com.pnuema.android.bible.data.firefly.Book
import com.pnuema.android.bible.data.firefly.ChapterCount
import com.pnuema.android.bible.data.firefly.Verse
import com.pnuema.android.bible.data.firefly.VerseCount
import com.pnuema.android.bible.data.firefly.Version

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IFireflyAPI {
    @GET("versions")
    fun getVersions(@Query("lang") language: String?): Call<List<Version>>

    @GET("books")
    fun getBooks(@Query("v") version: String?): Call<List<Book>>

    @GET("books/{book}/chapters")
    fun getChapterCount(@Path("book") book: Int?, @Query("v") version: String?): Call<ChapterCount>

    @GET("books/{book}/chapters/{chapter}/verses")
    fun getVerseCount(@Path("book") book: String, @Path("chapter") chapter: String, @Query("v") version: String?): Call<VerseCount>

    @GET("books/{book}/chapters/{chapter}")
    fun getChapterVerses(@Path("book") book: String, @Path("chapter") chapter: String, @Query("v") version: String?): Call<List<Verse>>
}
