package com.example.composejoyride.ui.screens

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.ui.theme.LocalTheme
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.rememberFirebaseAuthLauncher
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthScreen(navController: NavController, isBottomBarVisible: MutableState<Boolean>) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val buttonColor = MaterialTheme.colorScheme.secondary
    val textColor = MaterialTheme.colorScheme.tertiary
    val font = TheFont
    isBottomBarVisible.value = false
    val context = LocalContext.current
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            if (result.resultCode == RESULT_OK) {
                onSuccess(navController, isBottomBarVisible)
            }
        },
        onAuthError = {
            onError(it?.message ?: "Неизвестно", context)
        }
    )

    val signInIntent = remember {
        val themeId = if (!LocalTheme.value) R.style.FirebaseUI_Dark else R.style.FirebaseUI_Light
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.EmailBuilder().build()
                )
            )
            .setTheme(themeId)
            .setLogo(R.drawable.alfa_logo)
            .build()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Center,
                fontFamily = font,
                color = textColor,
                fontSize = 32.sp
            )

            OutlinedButton(
                onClick = { launcher.launch(signInIntent) },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Войти по email",
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Войти по Email",
                    fontFamily = font,
                    color = textColor,
                    fontSize = 18.sp
                )
            }

            OutlinedButton(
                onClick = {
                    signInAnonymously(
                    onSuccess = { navController.navigate(NoteGraph.MAIN_SCREEN) },
                    onError = {onError(it, context)}
                )},
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonOutline,
                    contentDescription = "Гость",
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Продолжить как гость",
                    fontFamily = font,
                    color = textColor,
                    fontSize = 18.sp
                )
            }
        }
    }
}

fun signInAnonymously(onSuccess: () -> Unit, onError: (String) -> Unit) {
    FirebaseAuth.getInstance()
        .signInAnonymously()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.message ?: "Ошибка авторизации")
            }
        }
}

fun onSuccess(navController: NavController, isBottomBarVisible: MutableState<Boolean>){
    navController.navigate(NoteGraph.MAIN_SCREEN)
    isBottomBarVisible.value = true
}

fun onError(error: String, context: Context){
    Toast.makeText(context, "Ошибка входа: $error", Toast.LENGTH_LONG).show()
}
