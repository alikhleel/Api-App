package com.example.apiapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.apiapp.data.local.dao.MovieDao
import com.example.apiapp.data.local.entity.ResultsEntity
import com.example.apiapp.data.remote.MovieApi
import com.example.apiapp.model.Results
import java.io.IOException

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val isSearchEndPoint: Boolean,
    private val movieDao: MovieDao,
    private val searchQuery: String? = null
) : PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val cachedMovies = movieDao.getCachedMovies()
        return try {
            val currentPage = params.key ?: 1
            val movies = if (isSearchEndPoint)
                movieApi.searchMulti(
                    page = currentPage,
                    query = searchQuery.orEmpty(),
                )
            else
                movieApi.getUpcoming(
                    page = currentPage
                )
            val moviesList = movies.body()?.results?.map {
                ResultsEntity(
                    id = it.id ?: -1,
                    title = it.title.orEmpty(),
                    posterPath = it.posterPath.orEmpty(),
                    overView = it.overview.orEmpty(),
                    voteAverage = it.voteAverage ?: 0.0,
                    page = currentPage
                )
            }
            movieDao.insertMovies(moviesList.orEmpty())

            val pageToKeep = listOf(currentPage-1, currentPage, currentPage + 1)

            movieDao.deleteMoviesByPages(
                pageToKeep
            )

            LoadResult.Page(
                data = movies.body()?.results.orEmpty(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.body()?.results?.isEmpty() == true) null else movies.body()?.page!! + 1
            )
        } catch (exception: Exception) {
            LoadResult.Page(
                data = cachedMovies.map {
                    Results(
                        false,
                        id = it.id,
                        title = it.title,
                        overview = it.overView,
                        posterPath = it.posterPath,
                        voteAverage = it.voteAverage
                    )
                },
                prevKey = null,
                nextKey = null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }
}