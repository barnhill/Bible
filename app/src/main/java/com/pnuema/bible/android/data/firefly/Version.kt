package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVersion

data class Version(
        var version: String,
        var url: String,
        var publisher: String,
        var copyright: String,
        var copyright_info: String,
        override var language: String,
        override var abbreviation: String,
        override val id: Int
) : IVersion {

    override fun getDisplayText(): String {
        return version
    }
}
