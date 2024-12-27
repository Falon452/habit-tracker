package com.falon.habit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
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
import com.falon.habit.presentation.AndroidHabitsViewModel
import com.falon.habit.presentation.HabitsScreen
import com.falon.habit.presentation.Routes
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

//                    if (FirebaseAuth.getInstance().currentUser == null) {
//                        startSignInFlow()  // Launch Firebase UI sign-in flow if the user is not authenticated
//                    } else {
                        HabitsRoot()  // Proceed with your app's main functionality
//                    }
                }
            }
        }
    }


}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        HabitsRoot()
    }
}

@Composable
fun HabitsRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HABITS_SCREEN
    ) {
        composable(route = Routes.HABITS_SCREEN) {
            val viewModel = hiltViewModel<AndroidHabitsViewModel>()
            HabitsScreen(
                viewModel = viewModel,
            )
        }
    }
}
