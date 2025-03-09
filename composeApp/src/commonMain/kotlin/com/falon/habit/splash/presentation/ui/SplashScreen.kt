package com.falon.habit.splash.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.falon.habit.splash.presentation.viewmodel.SplashViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, navController, viewModel.effects) {
        lifecycleOwner.repeatOnLifecycle(STARTED) {
            viewModel.effects.collect {
                viewModel.consumeEffect()?.let {
                    viewModel.onEffect(
                        it,
                        navController,
                    )
                }
            }
        }
    }
}
