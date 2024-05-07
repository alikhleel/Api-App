package com.example.apiapp.presentation.screens.upcoming

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.apiapp.domain.upcoming.GetUpComingMoviesUseCase
import com.example.apiapp.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpComingMoviesViewModel @Inject constructor(
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase
) : ViewModel() {
    val lazyState = mutableStateOf(LazyGridState())
    var upComingMoviesState: MutableStateFlow<PagingData<Results>> =
        MutableStateFlow(PagingData.empty())

    init {
        getUpComingMovies()
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            getUpComingMoviesUseCase().distinctUntilChanged() // not important we can remove it
                .cachedIn(viewModelScope).collect {
                    upComingMoviesState.value = it
                }
        }
    }

}