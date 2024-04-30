package com.example.apiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.apiapp.Constants.MOVIE_IMAGE_BASE_URL
import com.example.apiapp.model.BackdropSizes
import com.example.apiapp.model.UIState
import com.example.apiapp.model.UpComingResponse
import com.example.apiapp.presentation.screens.upcoming.UpcComingMoviesViewModel
import com.example.apiapp.ui.theme.APIAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            APIAppTheme {
                val viewModel by viewModels<UpcComingMoviesViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primaryContainer
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
                        UpComingMoviesScreen(viewModel = viewModel)

                    }

                }
            }
        }
    }
}

@Composable
fun UpComingMoviesScreen(viewModel: UpcComingMoviesViewModel) {

    when (val upComingMovies = viewModel.upComingMovies.value) {
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UIState.Success -> {
            UpComingMovieList(
                upComingMovies = upComingMovies.data!!
            )
        }

        is UIState.Error -> {
            Text(text = upComingMovies.error ?: "Error")
        }

        is UIState.Empty -> {
            Text(text = "Empty")
        }
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UpComingMovieList(upComingMovies: UpComingResponse) {
    LazyColumn {
        items(upComingMovies.results) { movie ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                val imageUrl =
                    "$MOVIE_IMAGE_BASE_URL${BackdropSizes.SMALL.value}${movie.posterPath}"
                GlideImage(
                    model = imageUrl,
                    contentDescription = "Movie Poster",
                    loading = placeholder(ColorPainter(Color.Red)),
                    modifier = Modifier.size(150.dp, 150.dp)

                )
                Text(text = movie.title ?: "No Title")

            }

        }
    }
}
