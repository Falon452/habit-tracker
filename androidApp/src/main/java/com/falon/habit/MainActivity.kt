package com.falon.habit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.falon.habit.presentation.AndroidSocialCounterViewModel
import com.falon.habit.presentation.NoSocialMediasScreen
import com.falon.habit.presentation.Routes
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val response = IdpResponse.fromResultIntent(result.data)
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // Handle authenticated user (e.g., navigate to main screen)
        } else {
            // Handle sign-in failure
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (FirebaseAuth.getInstance().currentUser == null) {
                        startSignInFlow()  // Launch Firebase UI sign-in flow if the user is not authenticated
                    } else {
                        NoSocialMediasRoot()  // Proceed with your app's main functionality
                    }
                }
            }
        }
    }

    private fun startSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
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
            NoSocialMediasScreen(
                viewModel = viewModel,
            )
        }
    }
}
