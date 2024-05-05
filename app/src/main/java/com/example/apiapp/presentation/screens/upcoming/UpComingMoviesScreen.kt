package com.example.apiapp.presentation.screens.upcoming

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.apiapp.Constants
import com.example.apiapp.R
import com.example.apiapp.model.BackdropSizes
import com.example.apiapp.model.UpComingResponse


@Composable
fun UpComingMoviesScreen(navController: NavHostController, viewModel: UpComingMoviesViewModel) {
    val lazyGridState = rememberLazyGridState()
    val moviePagingItems = viewModel.upComingMoviesState.collectAsLazyPagingItems()
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primaryContainer
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,

            ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Film Roll",
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.TopCenter
            )

        }
        Box {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center,
                state = lazyGridState
            ) {
                items(moviePagingItems.itemCount) {

                    val movie = moviePagingItems[it]!!
                    if (movie.adult == false) {
                        val imageUrl =
                            "${Constants.MOVIE_IMAGE_BASE_URL}${BackdropSizes.SMALL.value}${movie.posterPath}"
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "",
                            modifier = Modifier.padding(2.dp),
                            contentScale = ContentScale.FillWidth,
                            error = painterResource(id = R.drawable.ic_launcher_background),
                            placeholder = ColorPainter(Color.Red)
                        )
                    }
                }
            }
            moviePagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        Row(
                            Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = moviePagingItems.loadState.refresh as LoadState.Error
                        Row(
                            Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = error.error.localizedMessage.orEmpty())
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        Row(
                            Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = moviePagingItems.loadState.append as LoadState.Error
                        Text(text = error.error.localizedMessage.orEmpty())
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UpComingMovieList(upComingMovies: UpComingResponse) {
//    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
//        items(upComingMovies.results.size) {
//            upComingMovies.results.forEach { movie ->
//                val imageUrl =
//                    "${Constants.MOVIE_IMAGE_BASE_URL}${BackdropSizes.SMALL.value}${movie.posterPath}"
//                GlideImage(
//                    model = imageUrl,
//                    contentDescription = "Movie Poster",
//                    loading = placeholder(ColorPainter(Color.Red)),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(250.dp),
//                    contentScale = ContentScale.Crop
//
//                )
//            }
//
//        }
//    }
    LazyColumn {
        items(upComingMovies.results) { movie ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                val imageUrl =
                    "${Constants.MOVIE_IMAGE_BASE_URL}${BackdropSizes.SMALL.value}${movie.posterPath}"
                GlideImage(
                    model = imageUrl,
                    contentDescription = "Movie Poster",
                    loading = placeholder(ColorPainter(Color.Red)),
                    modifier = Modifier.size(150.dp),

                    )
                Text(text = movie.title ?: "No Title")

            }

        }
    }
}
