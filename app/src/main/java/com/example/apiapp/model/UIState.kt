package com.example.apiapp.model

sealed class UIState<T : Any> {

    data class Loading<T : Any>(val nothing: Nothing? = null) : UIState<T>()
    data class Success<T : Any>(val data: T?) : UIState<T>()
    data class Error<T : Any>(val error: String) : UIState<T>()
    data class Empty<T : Any>(
        val title: String? = "",
        val message: String? = "",
    ) : UIState<T>()

}