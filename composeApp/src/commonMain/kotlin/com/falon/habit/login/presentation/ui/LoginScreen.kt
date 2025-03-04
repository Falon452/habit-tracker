package com.falon.habit.login.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.falon.habit.login.presentation.viewmodel.LoginViewModel
import habittracker.composeapp.generated.resources.Res
import habittracker.composeapp.generated.resources.icon_google
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
) {
    val state by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Sign in",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(32.dp)) {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Email") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Password") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))
                TextButton(onClick = viewModel::onForgotPassword) {
                    Text("Forgot Password?", color = MaterialTheme.colorScheme.tertiary)
                }

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.onLogin() },
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Sign in")
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                state.errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "or",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedButton(
                    onClick = { viewModel.onGoogleSignInClicked() },
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(32.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                    contentPadding = PaddingValues(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.icon_google),
                        contentDescription = "Google Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Sign in with Google")
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = viewModel::onNavigateToRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create an account", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onLoginSuccess()
        }
    }
}
