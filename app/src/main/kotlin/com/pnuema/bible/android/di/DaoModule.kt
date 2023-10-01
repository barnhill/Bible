package com.pnuema.bible.android.di

import com.pnuema.bible.android.database.FireflyDatabase
import com.pnuema.bible.android.database.VersionOfflineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DaoModule {
    @Provides
    @ViewModelScoped
    fun bindOfflineDao(): VersionOfflineDao = FireflyDatabase.getInstance().versionDao
}