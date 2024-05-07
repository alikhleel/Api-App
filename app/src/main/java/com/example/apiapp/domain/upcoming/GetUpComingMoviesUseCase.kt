package com.example.apiapp.domain.upcoming

import com.example.apiapp.data.repository.MovieRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetUpComingMoviesUseCase @Inject constructor(private val upComingRepository: MovieRepository) {
     operator fun invoke() = upComingRepository.getUpcomingMovies()
}