package com.example.apiapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.apiapp.presentation.screens.movieDetails.MovieDetailsScreen
import com.example.apiapp.presentation.screens.movieDetails.MovieDetailsViewModel
import com.example.apiapp.presentation.screens.onBoarding.OnBoardingScreen
import com.example.apiapp.presentation.screens.onBoarding.OnBoardingViewModel
import com.example.apiapp.presentation.screens.upcoming.UpComingMoviesScreen
import com.example.apiapp.presentation.screens.upcoming.UpComingMoviesViewModel

enum class Screen {
    ONBOARDING, HOME, Search, Profile, MOVIE_DETAILS
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object OnBoarding : NavigationItem(Screen.ONBOARDING.name)
    data object Search : NavigationItem(Screen.Search.name)
    data object Profile : NavigationItem(Screen.Profile.name)
    data object MovieDetails : NavigationItem(Screen.MOVIE_DETAILS.name)
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
        composable(NavigationItem.Search.route) {
            Surface {}
        }

        composable(
            NavigationItem.MovieDetails.route + "/{movieId}",
            arguments = listOf(
                navArgument(
                    "movieId"
                ) {
                    NavType.IntType
                }
            )
        ) {
            val viewModel: MovieDetailsViewModel = hiltViewModel()
            MovieDetailsScreen(viewModel = viewModel)
        }

        composable(NavigationItem.Home.route) {
            val viewModel: UpComingMoviesViewModel = hiltViewModel()
            UpComingMoviesScreen(navController, viewModel)
        }


    }
}


data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = "",
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem("Home", Icons.Filled.Home, NavigationItem.Home.route),
            BottomNavigationItem(
                "Search", Icons.Filled.Search, NavigationItem.Search.route
            ),
            BottomNavigationItem(
                "Profile", Icons.Filled.AccountCircle, NavigationItem.Profile.route
            ),
        )
    }
}


fun NavOptionsBuilder.popUpToTop(navController: NavHostController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
        saveState = true
    }
}