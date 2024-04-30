package com.example.apiapp.domain.upcoming

import com.example.apiapp.data.repository.UpComingRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetUpComingMoviesUseCase @Inject constructor(private val upComingRepository: UpComingRepository) {
    suspend operator fun invoke() = upComingRepository.getUpcomingMovies()
}