package com.example.apiapp.presentation.screens.upcoming

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.domain.upcoming.GetUpComingMoviesUseCase
import com.example.apiapp.model.UIState
import com.example.apiapp.model.UpComingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpComingMoviesViewModel @Inject constructor(
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase
) : ViewModel() {
    var upComingMovies: MutableState<UIState<UpComingResponse>> = mutableStateOf(UIState.Loading())

    init {
        getUpComingMovies()
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            when (val response = getUpComingMoviesUseCase()) {
                is UIState.Success -> upComingMovies.value = UIState.Success(response.data)
                is UIState.Error -> upComingMovies.value = UIState.Error(response.error)
                is UIState.Empty -> upComingMovies.value = UIState.Empty(title = response.title)
                is UIState.Loading -> upComingMovies.value = UIState.Loading()
            }
        }
    }


}