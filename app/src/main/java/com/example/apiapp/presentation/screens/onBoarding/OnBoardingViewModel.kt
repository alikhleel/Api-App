package com.example.apiapp.presentation.screens.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.domain.upcoming.onBoarding.GetIsSafeFromDataStoreUseCase
import com.example.apiapp.domain.upcoming.onBoarding.SaveIsFirstTimeInDataStoreUseCase
import com.example.apiapp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveIsFirstTimeInDataStoreUseCase: SaveIsFirstTimeInDataStoreUseCase,
    private val getIsSafeFromDataStoreUseCase: GetIsSafeFromDataStoreUseCase
) : ViewModel() {
    val onBoardingCompleted = MutableStateFlow(false)
    var startDestination: String = Screen.ONBOARDING.name

    init {
        getOnBoardingState()
    }

    private fun getOnBoardingState() {
        viewModelScope.launch {
            onBoardingCompleted.value =
                getIsSafeFromDataStoreUseCase().stateIn(viewModelScope).value
            startDestination = if (onBoardingCompleted.value) {
                Screen.HOME.name
            } else {
                Screen.ONBOARDING.name
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