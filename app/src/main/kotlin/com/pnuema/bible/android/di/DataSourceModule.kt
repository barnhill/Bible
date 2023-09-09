package com.pnuema.bible.android.di

import com.pnuema.bible.android.datasource.FireflyDataSource
import com.pnuema.bible.android.datasource.FireflyDataSourceImpl
import com.pnuema.bible.android.datasource.LocalDataSource
import com.pnuema.bible.android.datasource.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {
    @Provides
    @ViewModelScoped
    fun bindFireflyDataSource(): FireflyDataSource = FireflyDataSourceImpl()

    @Provides
    @ViewModelScoped
    fun bindLocalDataSource(): LocalDataSource = LocalDataSourceImpl()
}