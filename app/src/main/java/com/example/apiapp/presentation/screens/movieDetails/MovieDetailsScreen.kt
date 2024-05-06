package com.example.apiapp.presentation.screens.movieDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.apiapp.Constants.MOVIE_IMAGE_BASE_URL
import com.example.apiapp.R
import com.example.apiapp.model.BackdropSizes
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.UIState


@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primaryContainer
    ) {
        val state = viewModel.movieDetailsState.collectAsState()
        when (val movieDetailsState = state.value) {
            is UIState.Loading -> {
                CircularProgressIndicator()
            }

            is UIState.Success -> {
                MovieDetails(movieDetailsState.data!!)
            }

            is UIState.Empty -> {
                Text(text = "Empty")
            }

            is UIState.Error -> {
                Text(text = movieDetailsState.error)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetails(movieDetailsResponse: MovieDetailsResponse) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        stickyHeader {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 16.dp),
                shape = RectangleShape,
            ) {


                AsyncImage(
                    model = "${MOVIE_IMAGE_BASE_URL}${BackdropSizes.MEDIUM.value}${movieDetailsResponse.posterPath}",
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                    error = painterResource(id = R.drawable.background),
                    placeholder = ColorPainter(Color.Gray)
                )
            }

        }

        item {
            Text(
                text = movieDetailsResponse.title, color = Color.Black

            )
        }
        item {
            Text(
                text = movieDetailsResponse.overview, color = Color.Black, fontSize = 20.sp,
            )
        }

    }
}
