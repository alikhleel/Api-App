package com.example.apiapp.domain.upcoming.onBoarding

import com.example.apiapp.data.dataStore.MovieAppDataStore
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SaveIsFirstTimeInDataStoreUseCase @Inject constructor(
    private val dataStore: MovieAppDataStore
) {
    suspend operator fun invoke(showTipsPage: Boolean) {
        dataStore.saveOnBoardingState(showTipsPage)
    }
}