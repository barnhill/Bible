package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.data.IVerseProvider
import kotlinx.serialization.Serializable

@Serializable
class VersesDomain(override val verses: List<IVerse>) : IVerseProvider
