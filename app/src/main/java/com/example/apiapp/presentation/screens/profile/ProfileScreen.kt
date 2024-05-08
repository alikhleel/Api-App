package com.example.apiapp.presentation.screens.profile

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.apiapp.model.UIState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController, viewModel: ProfileViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.createNewToken()
    }
    val userTokenState = viewModel.userTokenState.collectAsState()
    val userSessionState = viewModel.userSessionState.collectAsState()
    val userAccountState = viewModel.userAccountState.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var generatedUserToken by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.wrapContentSize(),
                    sheetState = sheetState,
                    onDismissRequest = {
                        showBottomSheet = false
                        if (generatedUserToken.isNotEmpty()) {
                            viewModel.createNewSession(generatedUserToken)
                        }
                        navController.popBackStack()

                    },
                    windowInsets = WindowInsets.waterfall
                ) {
                    WebViewScreen("https://www.themoviedb.org/authenticate/${generatedUserToken}")
                }
            }

            when (val userTokenResult = userTokenState.value) {
                is UIState.Success -> {
                    showBottomSheet = true
                    generatedUserToken = userTokenResult.data?.requestToken.orEmpty()
                }

                is UIState.Error -> {}
                is UIState.Empty -> {}
                is UIState.Loading -> {}
            }
            when (val session = userSessionState.value) {
                is UIState.Success -> {
                    showBottomSheet = false
                    val generatedSessionId = session.data?.sessionId
                    if (generatedSessionId?.isNotEmpty() == true) {
                        LaunchedEffect(viewModel) {
                            viewModel.getProfile(generatedSessionId.toString())
                        }
                    }
                }

                is UIState.Error -> {}
                is UIState.Empty -> {}
                is UIState.Loading -> {}
            }
            when (val account = userAccountState.value) {
                is UIState.Success -> {
                    val username = account.data?.username.toString()
                    val accountId = account.data?.id.toString()
                    Text(text = "Username: $username")
                    Text(text = "Account ID: $accountId")
                }

                is UIState.Error -> {}
                is UIState.Empty -> {}
                is UIState.Loading -> {}
            }

        }
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },

        update = { webView ->
            webView.loadUrl(url)
        },
    )
}