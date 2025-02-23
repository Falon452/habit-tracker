package com.falon.login.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.falon.login.R

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Sign in",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                TextButton(
                    onClick = {/* Handle Forgot Password */ },
                ) {
                    Text("Forgot Password?", color = MaterialTheme.colorScheme.tertiary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { /* Handle Login */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sign in")
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "or",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedButton(
                    onClick = { /* Handle Google Sign In */ },
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
                        painter = painterResource(id = R.drawable.icon_google),
                        contentDescription = "Google Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Sign in with Google")
                }

                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = { /* Handle Create Account */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create an account", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
