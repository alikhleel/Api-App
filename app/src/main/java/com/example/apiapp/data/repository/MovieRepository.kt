package com.example.apiapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.apiapp.data.local.dao.MovieDao
import com.example.apiapp.data.paging.MoviePagingSource
import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.Results
import com.example.apiapp.model.UIState
import com.example.apiapp.model.UserAccount
import com.example.apiapp.model.UserTokenResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi, private val movieDao: MovieDao
) {
    fun getUpcomingMovies(): Flow<PagingData<Results>> {
        return Pager(config = PagingConfig(
            pageSize = 20, prefetchDistance = 2
        ), pagingSourceFactory = {
            MoviePagingSource(
                movieApi, isSearchEndPoint = false, movieDao = movieDao
            )
        }).flow
    }

    fun searchMulti(query: String): Flow<PagingData<Results>> {
        return Pager(config = PagingConfig(
            pageSize = 20, prefetchDistance = 2
        ), pagingSourceFactory = {
            MoviePagingSource(
                movieApi, isSearchEndPoint = true, searchQuery = query, movieDao = movieDao
            )
        }).flow
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

    suspend fun createUserToken(): UIState<UserTokenResponse> {
        return try {
            val response = movieApi.createUserToken()
            if (response.isSuccessful && response.body() != null) {
                UIState.Success(response.body())
            } else {
                UIState.Empty(message = response.message().toString())
            }
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }

    suspend fun createNewSession(requestToken: String): UIState<UserTokenResponse> {
        return try {
            val response = movieApi.createSession(requestToken = requestToken)
            if (response.isSuccessful && response.body() != null) {
                UIState.Success(response.body())
            } else {
                UIState.Empty(message = response.message().toString())
            }
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }

    suspend fun getUserAccount(sessionId: String): UIState<UserAccount> {
        return try {
            val response = movieApi.getUserAccount(sessionId = sessionId)
            if (response.isSuccessful && response.body() != null) {
                UIState.Success(response.body())
            } else {
                UIState.Empty(message = response.message().toString())
            }
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }
}