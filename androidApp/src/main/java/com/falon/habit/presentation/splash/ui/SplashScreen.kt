package com.falon.habit.presentation.splash.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.falon.habit.R
import com.falon.habit.presentation.Routes
import com.falon.habit.presentation.splash.router.SplashRouter
import com.falon.habit.presentation.splash.viewmodel.AndroidSplashViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: AndroidSplashViewModel = hiltViewModel(),
) {
    val alpha = remember {
        Animatable(0f)
    }
    val scale = remember { Animatable(0.8f) }
    val router = remember {
        SplashRouter {
            navController.navigate(Routes.HABITS_SCREEN)
        }
    }
    LaunchedEffect(Unit) {
        alpha.animateTo(
            1f,
            animationSpec = tween(1500),
        )
    }
    LaunchedEffect(Unit) {
        scale.animateTo(
            1f,
            animationSpec = tween(1500),
        )
        viewModel.onInit()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effects.collect {
                viewModel.consumeEffect()?.let {
                    viewModel.onEffect(it, router)
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.unicorn),
            contentDescription = "Unicorn",
            modifier = Modifier.scale(scale.value).alpha(alpha.value)
        )
    }
}