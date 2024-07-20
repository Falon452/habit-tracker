package com.falon.nosocialmedia.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.falon.nosocialmedia.Greeting
import com.falon.nosocialmedia.android.socialcounter.presentation.AndroidSocialCounterViewModel
import com.falon.nosocialmedia.android.socialcounter.presentation.Routes
import com.falon.nosocialmedia.socialcounter.presentation.viewmodel.NoSocialMediasViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
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
        }
    }
}
