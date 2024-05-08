package com.example.apiapp.domain.authentication

import com.example.apiapp.data.repository.MovieRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class CreateNewTokenUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() = movieRepository.createUserToken()
}