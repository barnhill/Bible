package com.pnuema.bible.android.di

import com.pnuema.bible.android.repository.BaseRepository
import com.pnuema.bible.android.repository.FireflyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindFireflyRepository(
        fireflyRepository: FireflyRepository
    ): BaseRepository
}
