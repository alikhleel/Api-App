package com.example.apiapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.apiapp.BuildConfig
import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.model.Results
import okio.IOException


class MoviePagingSource(
    private val movieApi: MovieApi,
) : PagingSource<Int, Results>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            val currentPage = params.key ?: 1
            val movies = movieApi.getUpcoming(
                apiKey = BuildConfig.TMDB_API_KEY, page = currentPage
            )
            LoadResult.Page(
                data = movies.body()?.results.orEmpty(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.body()?.results?.isEmpty() == true) null else currentPage + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

}


class MovieSearchPagingSource(
    private val movieApi: MovieApi, private val query: String
) : PagingSource<Int, Results>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            val currentPage = params.key ?: 1
            val movies = movieApi.searchMulti(
                apiKey = BuildConfig.TMDB_API_KEY, page = currentPage, query = query,
            )
            LoadResult.Page(
                data = movies.body()?.results.orEmpty(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.body()?.results?.isEmpty() == true) null else currentPage + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

}