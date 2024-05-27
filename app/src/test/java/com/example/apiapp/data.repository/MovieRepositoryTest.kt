package com.example.apiapp.data.repository

import com.example.apiapp.BuildConfig
import com.example.apiapp.Constants
import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.Results
import com.example.apiapp.model.UpComingResponse
import com.google.gson.GsonBuilder
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepositoryTest {
    private lateinit var server: MockWebServer
    private lateinit var api: MovieApi

    @Before
    fun setUp() {
        server = MockWebServer()
        api = Retrofit.Builder().baseUrl(server.url("/${Constants.MOVIE_BASE_URL}"))
            .addConverterFactory(GsonConverterFactory.create()).build().create(MovieApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getMovieDetails() = runTest {
        val dto = MovieDetailsResponse()
        val gson = GsonBuilder().create()
        val json = gson.toJson(dto)
        val res = MockResponse()

        res.setBody(json)
        server.enqueue(res)

        val data = api.getMovieDetails(1, BuildConfig.TMDB_API_KEY)
        server.takeRequest()

        assertEquals(data.body(), dto)
    }

    @Test
    fun `get MovieDetails, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)
        val data = api.getMovieDetails(1, BuildConfig.TMDB_API_KEY)
        server.takeRequest()
        assertEquals(data.code(), 404)
        assertEquals(data.isSuccessful, false)
    }

    @Test
    fun getUpComingMovies() = runTest {
        val dto = UpComingResponse()
        val gson = GsonBuilder().create()
        val json = gson.toJson(dto)
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)
        val data = api.getUpcoming()
        server.takeRequest()

        assertEquals(data.body(), dto)
    }
}