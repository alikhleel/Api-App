package com.example.apiapp.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.apiapp.Constants.MOVIE_BASE_URL
import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.data.remote.MovieDetailsApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val httpclient =OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .connectTimeout(120L, TimeUnit.SECONDS)
            .readTimeout(120L, TimeUnit.SECONDS).writeTimeout(120L, TimeUnit.SECONDS)
        return httpclient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): MovieApi {
        return Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsApi(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): MovieDetailsApi {
        return Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(MovieDetailsApi::class.java)
    }
}