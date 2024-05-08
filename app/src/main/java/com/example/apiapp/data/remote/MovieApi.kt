package com.example.apiapp.data.remote

import com.example.apiapp.BuildConfig
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.UpComingResponse
import com.example.apiapp.model.UserAccount
import com.example.apiapp.model.UserTokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<UpComingResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<MovieDetailsResponse>

    @GET("3/search/movie")
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<UpComingResponse>


    @GET("3/authentication/token/new")
    suspend fun createUserToken(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<UserTokenResponse>


    @POST("3/authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("request_token") requestToken: String
    ): Response<UserTokenResponse>


    @GET("3/account")
    suspend fun getUserAccount(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("session_id") sessionId: String
    ): Response<UserAccount>

}
