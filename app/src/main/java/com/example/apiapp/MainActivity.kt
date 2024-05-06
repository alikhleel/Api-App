package com.example.apiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.apiapp.presentation.navigation.AppNavHost
import com.example.apiapp.presentation.navigation.BottomNavigationItem
import com.example.apiapp.presentation.navigation.NavigationItem
import com.example.apiapp.presentation.navigation.popUpToTop
import com.example.apiapp.presentation.screens.onBoarding.OnBoardingViewModel
import com.example.apiapp.ui.theme.APIAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val onBoardingViewModel: OnBoardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !onBoardingViewModel.isLoading.value
        }

        setContent {
            APIAppTheme {
                val screen = onBoardingViewModel.startDestination
                val navController = rememberNavController()
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    NavigationItem.OnBoarding.route -> false
                    else -> true
                }
                val navigationSelectionItem = rememberSaveable {
                    mutableIntStateOf(0)
                }
                Scaffold(modifier = Modifier.fillMaxWidth(), bottomBar = {
                    if (showBottomBar) {
                        NavigationBar(

                        ) {
                            BottomNavigationBar(
                                navigationSelectionItem, navController
                            )
                        }
                    }
                }) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AppNavHost(navController = navController, startDestination = screen)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RowScope.BottomNavigationBar(
    navigationSelectionItem: MutableIntState, navController: NavHostController
) {
    BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
        NavigationBarItem(icon = {
            Icon(
                navigationItem.icon, contentDescription = navigationItem.label
            )
        },
            label = { Text(navigationItem.label) },
            selected = navigationSelectionItem.intValue == index,
            onClick = {
                navigationSelectionItem.intValue = index
                navController.navigate(navigationItem.route) {
                    popUpToTop(navController)
                    restoreState = true
                    launchSingleTop = true
                }
            },
            modifier = Modifier.combinedClickable {
                if (navigationSelectionItem.intValue == index) {
                    navController.navigate(navigationItem.route) {
                        popUpToTop(navController)
                        restoreState = true
                        launchSingleTop = true

                    }

                }
            }
        )

    }
}