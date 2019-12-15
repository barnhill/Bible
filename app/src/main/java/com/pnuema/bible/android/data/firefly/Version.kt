package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVersion

data class Version(
        override var id: Int,
        override var abbreviation: String,
        override var language: String,
        override val displayText: String,
        var version: String,
        var url: String,
        var publisher: String,
        var copyright: String,
        var copyright_info: String
) : IVersion
