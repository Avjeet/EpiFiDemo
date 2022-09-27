package com.express.epifidemo.di.modules

import com.express.epifidemo.model.OMDBApiService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DataSourceEntryPoint {
    var omdbApiService: OMDBApiService
}