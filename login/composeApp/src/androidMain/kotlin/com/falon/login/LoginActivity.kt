package com.falon.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.falon.habit.theme.AppTheme
import com.falon.login.ui.LoginScreen

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                LoginScreen()
            }
        }
    }
}
