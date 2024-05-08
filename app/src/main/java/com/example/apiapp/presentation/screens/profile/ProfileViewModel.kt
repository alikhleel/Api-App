package com.example.apiapp.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.domain.account.GetUserAccount
import com.example.apiapp.domain.authentication.CreateNewSessionUseCase
import com.example.apiapp.domain.authentication.CreateNewTokenUseCase
import com.example.apiapp.model.UIState
import com.example.apiapp.model.UserAccount
import com.example.apiapp.model.UserTokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val createNewTokenUseCase: CreateNewTokenUseCase,
    private val createNewSessionUseCase: CreateNewSessionUseCase,
    private val getProfileUseCase: GetUserAccount,
) : ViewModel() {

    var userTokenState: MutableStateFlow<UIState<UserTokenResponse>> =
        MutableStateFlow(UIState.Loading())

    var userSessionState: MutableStateFlow<UIState<UserTokenResponse>> =
        MutableStateFlow(UIState.Loading())

    var userAccountState: MutableStateFlow<UIState<UserAccount>> =
        MutableStateFlow(UIState.Loading())


    fun createNewToken() {
        viewModelScope.launch {
            when (val response = createNewTokenUseCase()) {
                is UIState.Success -> {
                    userTokenState.value = UIState.Success(response.data)
                }

                is UIState.Error -> {
                    userTokenState.value = UIState.Error(response.error)
                }

                is UIState.Empty -> {
                    userTokenState.value = UIState.Empty(title = response.message)
                }

                is UIState.Loading -> {
                    userTokenState.value = UIState.Loading()
                }
            }
        }
    }

    fun createNewSession(requestToken: String) {
        viewModelScope.launch {
            when (val response = createNewSessionUseCase(requestToken)) {
                is UIState.Success -> {
                    userSessionState.value = UIState.Success(response.data!!)
                }

                is UIState.Error -> {
                    userSessionState.value = UIState.Error(response.error)
                }

                is UIState.Empty -> {
                    userSessionState.value = UIState.Empty(title = response.message)
                }

                is UIState.Loading -> {
                    userSessionState.value = UIState.Loading()
                }
            }
        }
    }

    fun getProfile(sessionId: String) {
        if (sessionId.isEmpty()) return
        viewModelScope.launch {
            when (val response = getProfileUseCase(sessionId)) {
                is UIState.Success -> {
                    userAccountState.value = UIState.Success(response.data!!)
                }

                is UIState.Error -> {
                    userAccountState.value = UIState.Error(response.error)
                }

                is UIState.Empty -> {
                    userAccountState.value = UIState.Empty(title = response.message)
                }

                is UIState.Loading -> {
                    userAccountState.value = UIState.Loading()
                }
            }
        }
    }
}