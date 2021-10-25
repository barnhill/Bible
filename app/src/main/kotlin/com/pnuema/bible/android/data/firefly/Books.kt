package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.data.IBookProvider

class Books(override val books: List<IBook>) : IBookProvider
