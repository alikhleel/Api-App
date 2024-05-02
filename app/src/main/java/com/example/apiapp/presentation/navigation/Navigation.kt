package com.example.apiapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apiapp.presentation.screens.onBoarding.OnBoardingScreen
import com.example.apiapp.presentation.screens.onBoarding.OnBoardingViewModel
import com.example.apiapp.presentation.screens.upcoming.UpComingMoviesScreen
import com.example.apiapp.presentation.screens.upcoming.UpComingMoviesViewModel

enum class Screen {
    ONBOARDING, AUTHENTICATION, HOME,
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object OnBoarding : NavigationItem(Screen.ONBOARDING.name)
    data object Authentication : NavigationItem(Screen.AUTHENTICATION.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.OnBoarding.route,
) {
    val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = onBoardingViewModel.startDestination
    ) {
        composable(NavigationItem.OnBoarding.route) {
            OnBoardingScreen(navController, onBoardingViewModel)
        }
        composable(NavigationItem.Authentication.route) {
            // AuthenticationScreen(navController)
        }

        composable(NavigationItem.Home.route) {
            val viewModel: UpComingMoviesViewModel = hiltViewModel()

            UpComingMoviesScreen(navController, viewModel)
        }
    }
}