package com.example.apiapp.domain.search

import com.example.apiapp.data.repository.MovieRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class QueryMultiUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(query: String) = movieRepository.searchMulti(query)
}