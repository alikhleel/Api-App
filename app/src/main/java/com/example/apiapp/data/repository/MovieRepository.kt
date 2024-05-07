package com.example.apiapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.apiapp.data.paging.MoviePagingSource
import com.example.apiapp.data.paging.MovieSearchPagingSource
import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.Results
import com.example.apiapp.model.UIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    fun getUpcomingMovies(): Flow<PagingData<Results>> {
        return Pager(config = PagingConfig(
            pageSize = 20, prefetchDistance = 2
        ), pagingSourceFactory = { MoviePagingSource(movieApi) }).flow
    }

    fun searchMulti(query: String): Flow<PagingData<Results>> {
        return Pager(config = PagingConfig(
            pageSize = 20, prefetchDistance = 2
        ), pagingSourceFactory = { MovieSearchPagingSource(movieApi, query) }).flow
    }


    suspend fun getMovieDetails(movieId: Int): UIState<MovieDetailsResponse> {
        return try {
            val response = movieApi.getMovieDetails(movieId = movieId)
            if (response.isSuccessful) {
                response.body()?.let {
                    UIState.Success(it)
                } ?: UIState.Error("An unknown error occurred")
            } else {
                UIState.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            UIState.Error(e.message ?: "An unknown error occurred")
        }
    }
}