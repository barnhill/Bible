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

package com.pnuema.android.bible.ui;

import android.content.Context;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener;
import com.pnuema.android.bible.ui.fragments.BookSelectionFragment;
import com.pnuema.android.bible.ui.fragments.ChapterSelectionFragment;
import com.pnuema.android.bible.ui.fragments.VerseSelectionFragment;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private final Context mContext;
    private final BCVSelectionListener listener;

    public SectionsPagerAdapter(final FragmentManager fm, final Context context, final BCVSelectionListener listener) {
        super(fm);

        mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment getItem(final int position) {
        if (position == TAB_ORDER.BOOKS.value) {
            return BookSelectionFragment.newInstance(listener);
        } else if (position == TAB_ORDER.CHAPTERS.value) {
            return ChapterSelectionFragment.newInstance(listener);
        } else if (position == TAB_ORDER.VERSES.value) {
            return VerseSelectionFragment.newInstance(listener);
        } else {
            throw new IllegalArgumentException("Invalid tab index selected");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        if (position == TAB_ORDER.BOOKS.value) {
            return mContext.getString(R.string.book).toUpperCase(Locale.getDefault());
        } else if (position == TAB_ORDER.CHAPTERS.value) {
            return mContext.getString(R.string.chapter).toUpperCase(Locale.getDefault());
        } else if (position == TAB_ORDER.VERSES.value) {
            return mContext.getString(R.string.verse).toUpperCase(Locale.getDefault());
        } else {
            return null;
        }
    }

    private enum TAB_ORDER {
        BOOKS(0), CHAPTERS(1), VERSES(2);
        private final int value;

        TAB_ORDER(final int value) {
            this.value = value;
        }
    }
}
