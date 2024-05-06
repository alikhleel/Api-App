package com.example.apiapp.presentation.screens.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.domain.movieDetails.GetMovieDetailsUseCase
import com.example.apiapp.model.MovieDetailsResponse
import com.example.apiapp.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {
    private val movieId: String = checkNotNull(savedStateHandle["movieId"])

    val movieDetailsState =
        MutableStateFlow<UIState<MovieDetailsResponse>>(UIState.Empty())

    init {
        getMovieDetails(movieId.toInt())
    }

    private fun getMovieDetails(movieId: Int) {
        movieDetailsState.value = UIState.Loading()
        viewModelScope.launch {
            movieDetailsState.value = getMovieDetailsUseCase(movieId)
        }
    }
}