package com.falon.habit.login.presentation.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.falon.habit.login.presentation.auth.GoogleAuthUiProvider
import com.falon.habit.login.presentation.viewmodel.LoginViewModel
import habittracker.composeapp.generated.resources.Res
import habittracker.composeapp.generated.resources.icon_google
import org.jetbrains.compose.resources.painterResource


@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    loginViewModel: LoginViewModel,
    googleAuthUiProvider: GoogleAuthUiProvider,
) {
    OutlinedButton(
        onClick = { loginViewModel.onSignInClicked(googleAuthUiProvider) },
        enabled = !isLoading,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
        contentPadding = PaddingValues(6.dp),
        modifier = modifier,
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
}
