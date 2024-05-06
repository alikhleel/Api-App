package com.example.apiapp.data.repository

import com.example.apiapp.data.remote.MovieDetailsApi
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.UIState
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieDetailsRepository @Inject constructor(
    private val movieDetailsApi: MovieDetailsApi
) {

    suspend fun getMovieDetails(movieId: Int): UIState<MovieDetailsResponse> {
        return try {
            val response = movieDetailsApi.getMovieDetails(movieId = movieId)
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