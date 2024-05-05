package com.example.apiapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.apiapp.data.paging.MoviePagingSource
import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.model.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UpComingRepository @Inject constructor(
    private val movieApi: MovieApi
) {
//    suspend fun getUpcomingMovies(): UIState<UpComingResponse> {
//        return try {
//            val response = movieApi.getUpcoming()
//            if (response.isSuccessful && response.body() != null) {
//                UIState.Success(response.body())
//            } else {
//                UIState.Empty(message = response.message().toString())
//            }
//        } catch (e: Exception) {
//            UIState.Error(e.message.toString())
//        }
//
//    }

    fun getUpcomingMovies(): Flow<PagingData<Results>> {
        return Pager(config = PagingConfig(
            pageSize = 20, prefetchDistance = 2
        ), pagingSourceFactory = { MoviePagingSource(movieApi) }).flow

    }

}