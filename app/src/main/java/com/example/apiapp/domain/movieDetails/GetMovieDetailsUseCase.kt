package com.example.apiapp.domain.movieDetails

import com.example.apiapp.data.repository.MovieDetailsRepository
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.UIState
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Int): UIState<MovieDetailsResponse> {
        return movieDetailsRepository.getMovieDetails(movieId)
    }
}