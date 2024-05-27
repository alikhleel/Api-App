package com.example.apiapp.domain.upcoming

import com.example.apiapp.data.repository.MovieRepository
import com.example.apiapp.data.repository.MovieRepositoryTest
import com.example.apiapp.domain.movieDetails.GetMovieDetailsUseCase
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.UIState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest : BaseTestCase() {

    @MockK(relaxed = true)
    lateinit var moviesRepository: MovieRepository


    @MockK(relaxed = true)
    lateinit var getMovieDetailUseCase: GetMovieDetailsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getMovieDetailUseCase = GetMovieDetailsUseCase(moviesRepository)
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    val dummy = UIState.Error<MovieDetailsResponse>("")

    @Test
    fun invoke() {
        runTest {
            val expected = dummy
            coEvery {
                moviesRepository.getMovieDetails(11)
            } returns expected

            val result = getMovieDetailUseCase(11)

            Assert.assertEquals(expected,result)

        }
    }
}