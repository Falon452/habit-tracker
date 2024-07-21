package com.falon.nosocialmedia.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.falon.nosocialmedia.android.socialcounter.presentation.AndroidSocialCounterViewModel
import com.falon.nosocialmedia.android.socialcounter.presentation.NoSocialMediasScreen
import com.falon.nosocialmedia.android.socialcounter.presentation.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NoSocialMediasRoot()
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        NoSocialMediasRoot()
    }
}

@Composable
fun NoSocialMediasRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.NO_SOCIAL_MEDIA
    ) {
        composable(route = Routes.NO_SOCIAL_MEDIA) {
            val viewModel = hiltViewModel<AndroidSocialCounterViewModel>()
            val state by viewModel.state.collectAsState()
            NoSocialMediasScreen(
                viewState = state,
                onSocialMediaClick = {viewModel.onSocialMediaClicked(it) },
            )
        }
    }
}
