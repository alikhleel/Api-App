package com.example.apiapp.domain.onBoarding

import com.example.apiapp.data.dataStore.MovieAppDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetIsSafeFromDataStoreUseCase @Inject constructor(
    private val dataStore: MovieAppDataStore
) {
    operator fun invoke(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }
}