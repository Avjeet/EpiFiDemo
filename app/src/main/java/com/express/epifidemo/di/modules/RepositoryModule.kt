package com.express.epifidemo.di.modules

import com.express.epifidemo.model.HomeMovieRepository
import com.express.epifidemo.model.HomeMovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindHomeGifRepository(homeGifRepository: HomeMovieRepositoryImpl): HomeMovieRepository
}