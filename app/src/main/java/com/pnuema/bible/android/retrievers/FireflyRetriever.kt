package com.pnuema.bible.android.retrievers

import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.data.firefly.*
import com.pnuema.bible.android.retrofit.FireflyAPI
import com.pnuema.bible.android.retrofit.IFireflyAPI
import com.pnuema.bible.android.statics.App
import com.pnuema.bible.android.statics.Constants
import com.pnuema.bible.android.statics.CurrentSelected
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FireflyRetriever : BaseRetriever() {
    companion object {
        fun get(): BaseRetriever {
            if (instance == null) {
                instance = FireflyRetriever()
            }
            return instance as BaseRetriever
        }
    }
    override fun savePrefs() {
        CurrentSelected.savePref(Constants.KEY_SELECTED_VERSION + tag, Gson().toJson(CurrentSelected.version))
        CurrentSelected.savePref(Constants.KEY_SELECTED_BOOK + tag, Gson().toJson(CurrentSelected.book))
        CurrentSelected.savePref(Constants.KEY_SELECTED_CHAPTER + tag, Gson().toJson(CurrentSelected.chapter))
        CurrentSelected.savePref(Constants.KEY_SELECTED_VERSE + tag, Gson().toJson(CurrentSelected.verse))
    }

    override fun readPrefs() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext())

        CurrentSelected.version = Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSION + tag, "kjv"), String::class.java)
        CurrentSelected.book = Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_BOOK + tag, "1"), Int::class.java)
        CurrentSelected.chapter = Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_CHAPTER + tag, "1"), Int::class.java)
        CurrentSelected.verse = Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSE + tag, "1"), Int::class.java)
    }

    override fun getVersions(): LiveData<Versions> {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getVersions(null) //TODO select language

        val liveVersions = MutableLiveData<Versions>()
        call.enqueue(object : Callback<List<Version>> {
            override fun onResponse(call: Call<List<Version>>, response: Response<List<Version>>) {
                if (!response.isSuccessful) {
                    return
                }

                val versions = response.body()
                if (versions == null || versions.isEmpty()) {
                    return
                }

                liveVersions.value = Versions(ArrayList<IVersion>(versions))
            }

            override fun onFailure(call: Call<List<Version>>, t: Throwable) {}
        })

        return liveVersions
    }

    override fun getChapters(book: String): LiveData<ChapterCount> {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getChapterCount(CurrentSelected.book, CurrentSelected.version)

        val liveChapterCount = MutableLiveData<ChapterCount>()
        call.enqueue(object : Callback<ChapterCount> {
            override fun onResponse(call: Call<ChapterCount>, response: Response<ChapterCount>) {
                val chapterCount = response.body()

                if (chapterCount == null || !response.isSuccessful) {
                    return
                }

                liveChapterCount.value = chapterCount
            }

            override fun onFailure(call: Call<ChapterCount>, t: Throwable) {}
        })

        return liveChapterCount
    }

    override fun getVerseCount(version: String, book: String, chapter: String): LiveData<VerseCount> {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getVerseCount(book, chapter, version)

        val liveVerseCount = MutableLiveData<VerseCount>()
        call.enqueue(object : Callback<VerseCount> {
            override fun onResponse(call: Call<VerseCount>, response: Response<VerseCount>) {
                val verseCount = response.body() ?: return

                liveVerseCount.value = verseCount
            }

            override fun onFailure(call: Call<VerseCount>, t: Throwable) {}
        })

        return liveVerseCount
    }

    override fun getBooks(): LiveData<Books> {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getBooks(CurrentSelected.version)

        val liveBooks = MutableLiveData<Books>()
        call.enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val books = response.body()
                if (books == null || books.isEmpty() || !response.isSuccessful) {
                    return
                }

                liveBooks.value = Books(ArrayList<IBook>(books))
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {}
        })

        return liveBooks
    }

    override fun getVerses(version: String, book: String, chapter: String): LiveData<Verses> {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getChapterVerses(book, chapter, version)

        val liveVerses = MutableLiveData<Verses>()
        call.enqueue(object : Callback<List<Verse>> {
            override fun onResponse(call: Call<List<Verse>>, response: Response<List<Verse>>) {
                val verses = response.body() ?: return

                liveVerses.value = Verses(ArrayList<IVerse>(verses))
            }

            override fun onFailure(call: Call<List<Verse>>, t: Throwable) {}
        })

        return liveVerses
    }
}
