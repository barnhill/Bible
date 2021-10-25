/*
 * Pnuema Productions LLC ("COMPANY") CONFIDENTIAL
 * Unpublished Copyright (c) 2009-2015 Pnuema Productions, All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains the property of COMPANY.
 * The intellectual and technical concepts contained herein are proprietary to COMPANY and may be
 * covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or
 * copyright law. Dissemination of this information or reproduction of this material is strictly
 * forbidden unless prior written permission is obtained from COMPANY.  Access to the source code
 * contained herein is hereby forbidden to anyone except current COMPANY employees, managers or
 * contractors who have executed Confidentiality and Non-disclosure agreements explicitly covering
 * such access.
 *
 * The copyright notice above does not evidence any actual or intended publication or disclosure of
 * this source code, which includes  information that is confidential and/or proprietary, and is a
 * trade secret, of  COMPANY.   ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE,
 * OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT  THE EXPRESS WRITTEN CONSENT
 * OF COMPANY IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL
 * TREATIES.  THE RECEIPT OR POSSESSION OF  THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT
 * CONVEY OR IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE,
 * USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
 */

package com.pnuema.bible.android.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pnuema.bible.android.R
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.fragments.BookSelectionFragment
import com.pnuema.bible.android.ui.fragments.ChapterSelectionFragment
import com.pnuema.bible.android.ui.fragments.VerseSelectionFragment
import java.util.*

/**
 * A [FragmentStateAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fragment: Fragment, private val context: Context, private val listener: BCVSelectionListener) : FragmentStateAdapter(fragment) {

    companion object {
        private const val BOOKS = 0
        private const val CHAPTERS = 1
        private const val VERSES = 2
    }

    fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            BOOKS -> context.getString(R.string.book).uppercase(Locale.getDefault())
            CHAPTERS -> context.getString(R.string.chapter).uppercase(Locale.getDefault())
            VERSES -> context.getString(R.string.verse).uppercase(Locale.getDefault())
            else -> null
        }
    }

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            BOOKS -> BookSelectionFragment.newInstance(listener)
            CHAPTERS -> ChapterSelectionFragment.newInstance(listener)
            VERSES -> VerseSelectionFragment.newInstance(listener)
            else -> throw IllegalArgumentException("Invalid tab index selected")
        }
    }
}
