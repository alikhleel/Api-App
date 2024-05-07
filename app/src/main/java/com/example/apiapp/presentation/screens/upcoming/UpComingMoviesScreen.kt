package com.example.apiapp.presentation.screens.upcoming

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
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
import com.example.apiapp.Constants
import com.example.apiapp.R
import com.example.apiapp.model.BackdropSizes
import com.example.apiapp.presentation.navigation.NavigationItem


@Composable
fun UpComingMoviesScreen(navController: NavHostController, viewModel: UpComingMoviesViewModel) {
    val moviePagingItems = viewModel.upComingMoviesState.collectAsLazyPagingItems()

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primaryContainer
    ) {


        Box {

            LazyVerticalGrid(
                state = viewModel.lazyState.value,
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center,
            ) {
                items(moviePagingItems.itemCount) {

                    val movie = moviePagingItems[it]!!
                    if (movie.adult == false) {
                        val imageUrl =
                            "${Constants.MOVIE_IMAGE_BASE_URL}${BackdropSizes.SMALL.value}${movie.posterPath}"
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate("${NavigationItem.MovieDetails.route}/${movie.id}")
                                },

                            ) {
                            AsyncImage(
                                model = imageUrl,
                                modifier = Modifier.fillMaxWidth(),
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,
                                error = painterResource(id = R.drawable.ic_launcher_background),
                                placeholder = ColorPainter(Color.Red)
                            )
                        }
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
