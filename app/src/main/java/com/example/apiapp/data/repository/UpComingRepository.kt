package com.example.apiapp.data.repository

import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.model.UIState
import com.example.apiapp.model.UpComingResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UpComingRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getUpcomingMovies(): UIState<UpComingResponse> {
        return try {
            val response = movieApi.getUpcoming()
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