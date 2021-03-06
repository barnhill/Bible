package com.pnuema.bible.android.api

import com.pnuema.bible.android.data.firefly.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IFireflyAPI {
    @GET("versions")
    suspend fun getVersions(@Query("lang") language: String?): List<Version>

    @GET("books")
    suspend fun getBooks(@Query("v") version: String?): List<Book>

    @GET("books/{book}/chapters")
    suspend fun getChapterCount(@Path("book") book: Int?, @Query("v") version: String?): ChapterCount

    @GET("books/{book}/chapters/{chapter}/verses")
    suspend fun getVerseCount(@Path("book") book: String, @Path("chapter") chapter: String, @Query("v") version: String?): VerseCount

    @GET("books/{book}/chapters/{chapter}")
    suspend fun getChapterVerses(@Path("book") book: String, @Path("chapter") chapter: String, @Query("v") version: String?): List<Verse>
}
