package com.pnuema.bible.android.statics

import android.content.Context
import android.net.ConnectivityManager

object ConnectionUtils {

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}
