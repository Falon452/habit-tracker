package com.falon.habit.presentation.splash.router

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.NavController
import com.falon.habit.presentation.Routes
import com.firebase.ui.auth.AuthUI

class AndroidSplashRouter(
    private val navController: NavController,
    private val signInLauncher: ActivityResultLauncher<Intent>
) : SplashRouter {

    override fun routeToSignIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    override fun routeToHabitsScreen() {
        navController.navigate(Routes.HABITS_SCREEN)
    }
}
