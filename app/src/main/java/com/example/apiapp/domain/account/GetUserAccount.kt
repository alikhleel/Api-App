package com.example.apiapp.domain.account

import com.example.apiapp.data.repository.MovieRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetUserAccount @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(sessionId: String) = movieRepository.getUserAccount(sessionId)
}