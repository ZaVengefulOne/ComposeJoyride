package com.example.composejoyride.ui.theme.composables

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (FirebaseAuthUIAuthenticationResult) -> Unit,
    onAuthError: (Exception?) -> Unit
): ManagedActivityResultLauncher<Intent, FirebaseAuthUIAuthenticationResult> {
    return rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onAuthComplete(result)
        } else {
            onAuthError(result.idpResponse?.error)
        }
    }
}
