package com.example.apiapp.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.apiapp.domain.search.QueryMultiUseCase
import com.example.apiapp.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchMultiViewModel @Inject constructor(
    private val queryMultiUseCase: QueryMultiUseCase
) : ViewModel() {


    var resultPaging: MutableStateFlow<PagingData<Results>> = MutableStateFlow(PagingData.empty())


//    fun onQueryChanged(query: String) {
//        this.query.value = query
//    }

    fun getMovies(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank()) queryMultiUseCase(query).cachedIn(viewModelScope).collect {
                resultPaging.value = it
            }
        }
    }


}