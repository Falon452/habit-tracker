package com.falon.habit.presentation.splash.ui

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.falon.habit.R
import com.falon.habit.presentation.splash.router.AndroidSplashRouter
import com.falon.habit.presentation.splash.viewmodel.AndroidSplashViewModel
import com.falon.login.LoginActivity
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: AndroidSplashViewModel = hiltViewModel(),
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.onSignInResult(result)
        }
    val context = LocalContext.current
    val router = remember {
        AndroidSplashRouter(
            navController = navController,
            loginActivityLauncher = {
                launcher.launch(Intent(context, LoginActivity::class.java))
            }
        )
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
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
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
        Image(
            painter = painterResource(id = R.drawable.unicorn),
            contentDescription = "Unicorn",
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }
}
