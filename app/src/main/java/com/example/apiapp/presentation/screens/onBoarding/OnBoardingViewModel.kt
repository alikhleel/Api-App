package com.example.apiapp.presentation.screens.onBoarding

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.domain.onBoarding.GetIsSafeFromDataStoreUseCase
import com.example.apiapp.domain.onBoarding.SaveIsFirstTimeInDataStoreUseCase
import com.example.apiapp.presentation.navigation.NavigationItem
import com.example.apiapp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveIsFirstTimeInDataStoreUseCase: SaveIsFirstTimeInDataStoreUseCase,
    private val getIsSafeFromDataStoreUseCase: GetIsSafeFromDataStoreUseCase
) : ViewModel() {
    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: MutableState<Boolean> = _isLoading


    val onBoardingCompleted = MutableStateFlow(false)
    var startDestination: String = NavigationItem.Home.route

    init {
        getOnBoardingState()
    }

    private fun getOnBoardingState() {
        viewModelScope.launch {
//            onBoardingCompleted.value =
//                getIsSafeFromDataStoreUseCase().stateIn(viewModelScope).value
            getIsSafeFromDataStoreUseCase().collect { completed ->
                onBoardingCompleted.value = completed
                startDestination = if (onBoardingCompleted.value) {
                    Screen.HOME.name
                } else {
                    Screen.ONBOARDING.name
                }
                _isLoading.value = false
            }
        }
    }

    fun saveOnBoardingState(showOnBoardingPage: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            saveIsFirstTimeInDataStoreUseCase(showOnBoardingPage)
//            onBoardingCompleted.value = showOnBoardingPage
        }
    }
}