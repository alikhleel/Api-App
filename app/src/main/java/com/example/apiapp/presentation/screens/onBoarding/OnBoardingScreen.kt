package com.example.apiapp.presentation.screens.onBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.model.TipPage
import com.example.apiapp.presentation.navigation.Screen
import com.example.apiapp.presentation.navigation.popUpToTop
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    navController: NavHostController, viewModel: OnBoardingViewModel
) {
    val onBoardingCompleted by viewModel.onBoardingCompleted.collectAsState()
    if (onBoardingCompleted) {
        navController.navigate(Screen.HOME.name) {
            popUpToTop(navController)
        }
    } else {
        OnBoardingTips(navController, viewModel)
    }

}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnBoardingTips(
    navController: NavHostController, viewModel: OnBoardingViewModel
) {
    val pagerState = rememberPagerState(0, pageCount = {
        3
    })
    val tips = listOf(
        TipPage.FirstPage, TipPage.SecondPage, TipPage.ThirdPage
    )


    val coroutineScope = rememberCoroutineScope()
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Box {
            Box(
                modifier = Modifier
                    .rotate(-90f)
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.BottomCenter)
                    .paint(
                        painter = painterResource(id = R.drawable.onboarding_waves),
                        contentScale = ContentScale.Crop
                    )
            )
            Column {

                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.secondary else Color.Gray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                ) { page ->
                    BoardingPage(tips[page])
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), onClick = {
                    if (pagerState.currentPage + 1 >= pagerState.pageCount) {
                        navController.navigate(Screen.HOME.name) {
                            popUpToTop(navController)
                        }
                        viewModel.saveOnBoardingState(true)
                    }
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }) {
                    Text(
                        text = if (pagerState.currentPage + 1 >= pagerState.pageCount) "Get Started" else "Next",
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BoardingPage(
    tipPage: TipPage
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = tipPage.image),
                contentDescription = null
            )
            Column {

                Text(text = tipPage.title)
                Text(text = tipPage.description)

            }

        }
    }
}