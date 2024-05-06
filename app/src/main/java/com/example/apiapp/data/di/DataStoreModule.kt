package com.example.apiapp.data.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.apiapp.data.dataStore.MovieAppDataStore
import com.example.apiapp.data.paging.MoviePagingSource
import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.model.Results
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ) = MovieAppDataStore(context)

}
