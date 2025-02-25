package com.falon.habit.splash.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.falon.habit.splash.router.AndroidSplashRouter
import com.falon.habit.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.launch
import kotlin.let

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel,
    routeToSignIn: () -> Unit,
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }
    val launcher = { routeToSignIn() }
    val router = remember {
        AndroidSplashRouter(
            routeToHabitsScreen = {

            },
            loginActivityLauncher = {
                launcher()
            }
        )
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_CREATE) {
                viewModel.onViewCreated()
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }
    val scope = rememberCoroutineScope()


    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            viewModel.effects.collect {
                viewModel.consumeEffect()?.let {
                    viewModel.onEffect(
                        it,
                        router,
                        { durationMillis ->
                            scope.launch {
                                alpha.animateTo(
                                    1f,
                                    animationSpec = tween(durationMillis.toInt()),
                                )
                            }
                        },
                        { durationMillis ->
                            scope.launch {
                                scale.animateTo(
                                    1f,
                                    animationSpec = tween(durationMillis.toInt()),
                                )
                            }
                        },
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
//        Image(
//            painter = painterResource(id = drawable.unicorn),
//            contentDescription = "Unicorn",
//            modifier = Modifier
//                .scale(scale.value)
//                .alpha(alpha.value)
//        )
    }
}
