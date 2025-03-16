package com.falon.habit.login.presentation.auth

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.falon.habit.R
import com.falon.habit.login.presentation.model.GoogleAccount
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

actual class GoogleAuthUiProvider(
    private val activityContext: Context,
    private val credentialManager: CredentialManager,
) {

    actual suspend fun signIn(): GoogleAccount? = try {
        val credential = credentialManager.getCredential(
            context = activityContext,
            request = getCredentialRequest()
        ).credential
        handleSignIn(credential)
    } catch (e: Exception) {
        Log.e(TAG, e.toString())
        null
    }

    private fun handleSignIn(credential: Credential): GoogleAccount? =
        when {
            credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                try {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    GoogleAccount(
                        token = googleIdTokenCredential.idToken,
                        displayName = googleIdTokenCredential.displayName ?: "",
                        profileImageUrl = googleIdTokenCredential.profilePictureUri?.toString()
                    )
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, e.toString())
                    null
                }
            }

            else -> null
        }

    private fun getCredentialRequest(): GetCredentialRequest =
        GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption())
            .build()

    private fun getGoogleIdOption(): GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .setServerClientId(activityContext.getString(R.string.default_web_client_id))
            .build()

    private companion object {

        const val TAG = "GoogleAuthUiProvider"
    }
}
