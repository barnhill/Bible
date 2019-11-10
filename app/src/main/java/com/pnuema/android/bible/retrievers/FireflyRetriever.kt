package com.pnuema.android.bible.retrievers

import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pnuema.android.bible.data.IBook
import com.pnuema.android.bible.data.IVerse
import com.pnuema.android.bible.data.IVersion
import com.pnuema.android.bible.data.firefly.*
import com.pnuema.android.bible.retrofit.FireflyAPI
import com.pnuema.android.bible.retrofit.IFireflyAPI
import com.pnuema.android.bible.statics.App
import com.pnuema.android.bible.statics.Constants
import com.pnuema.android.bible.statics.CurrentSelected
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FireflyRetriever : BaseRetriever() {
    override fun savePrefs() {
        CurrentSelected.savePref(Constants.KEY_SELECTED_VERSION + tag, Gson().toJson(CurrentSelected.getVersion()))
        CurrentSelected.savePref(Constants.KEY_SELECTED_BOOK + tag, Gson().toJson(CurrentSelected.getBook()))
        CurrentSelected.savePref(Constants.KEY_SELECTED_CHAPTER + tag, Gson().toJson(CurrentSelected.getChapter()))
        CurrentSelected.savePref(Constants.KEY_SELECTED_VERSE + tag, Gson().toJson(CurrentSelected.getVerse()))
    }

    override fun readPrefs() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext())

        CurrentSelected.setVersion(Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSION + tag, ""), String::class.java))
        CurrentSelected.setBook(Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_BOOK + tag, ""), Int::class.java))
        CurrentSelected.setChapter(Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_CHAPTER + tag, ""), Int::class.java))
        CurrentSelected.setVerse(Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSE + tag, ""), Int::class.java))
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

    override fun getChapters(book: String) {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getChapterCount(CurrentSelected.getBook(), CurrentSelected.getVersion())
        call.enqueue(object : Callback<ChapterCount> {
            override fun onResponse(call: Call<ChapterCount>, response: Response<ChapterCount>) {
                val chapterCount = response.body()

                if (chapterCount == null || !response.isSuccessful) {
                    return
                }

                setChanged()
                notifyObservers(chapterCount)
            }

            override fun onFailure(call: Call<ChapterCount>, t: Throwable) {}
        })
    }

    override fun getVerseCount(version: String, book: String, chapter: String) {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getVerseCount(book, chapter, version)
        call.enqueue(object : Callback<VerseCount> {
            override fun onResponse(call: Call<VerseCount>, response: Response<VerseCount>) {
                val verseCount = response.body() ?: return

                setChanged()
                notifyObservers(verseCount)
            }

            override fun onFailure(call: Call<VerseCount>, t: Throwable) {}
        })
    }

    override fun getBooks() {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getBooks(CurrentSelected.getVersion())
        call.enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val books = response.body()
                if (books == null || books.isEmpty() || !response.isSuccessful) {
                    return
                }

                setChanged()
                notifyObservers(Books(ArrayList<IBook>(books)))
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {}
        })
    }

    override fun getVerses(version: String, book: String, chapter: String) {
        val api = FireflyAPI.getInstance(App.getContext()).create(IFireflyAPI::class.java)
        val call = api.getChapterVerses(book, chapter, version)
        call.enqueue(object : Callback<List<Verse>> {
            override fun onResponse(call: Call<List<Verse>>, response: Response<List<Verse>>) {
                val verses = response.body() ?: return

                setChanged()
                notifyObservers(Verses(ArrayList<IVerse>(verses)))
            }

            override fun onFailure(call: Call<List<Verse>>, t: Throwable) {}
        })
    }
}
