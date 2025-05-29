package com.example.composejoyride.ui.screens

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.LocalTheme
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.rememberFirebaseAuthLauncher
import com.example.composejoyride.ui.viewModels.SettingsViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthScreen(
    navController: NavController,
    isBottomBarVisible: MutableState<Boolean>,
    //preferences: SharedPreferences
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val buttonColor = MaterialTheme.colorScheme.secondary
    val textColor = MaterialTheme.colorScheme.tertiary
    val font = TheFont
    val viewmodel: SettingsViewModel = sharedViewModel(navController)

    isBottomBarVisible.value = false
    val showAdminDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            val db = Firebase.firestore
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val userMap = mapOf(
                    "uid" to it.uid,
                    "email" to it.email,
                    "isAnonymous" to it.isAnonymous,
                    "timestamp" to System.currentTimeMillis()
                )
                db.collection("users").document(it.uid).set(userMap)
            }

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
                        onSuccess = { onSuccess(navController, isBottomBarVisible) },
                        onError = { onError(it, context) },
                        false
                    )
                },
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
                    text = "Войти как гость",
                    fontFamily = font,
                    color = textColor,
                    fontSize = 18.sp
                )
            }

            OutlinedButton(
                onClick = {
                    showAdminDialog.value = true
                },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = "Администратор",
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Войти как администратор",
                    fontFamily = font,
                    color = textColor,
                    fontSize = 18.sp
                )
            }

            if (showAdminDialog.value) {
                var login by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = { showAdminDialog.value = false },
                    title = { Text("Вход администратора", color = MaterialTheme.colorScheme.tertiary) },
                    containerColor = MaterialTheme.colorScheme.background,
                    text = {
                        Column {
                            OutlinedTextField(
                                value = login,
                                onValueChange = { login = it },
                                label = { Text("Логин") }
                            )
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Пароль") },
                                visualTransformation = PasswordVisualTransformation()
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (login == "admin" && password == "admin") {
                                showAdminDialog.value = false
                                signInAnonymously(
                                    onSuccess = { onSuccess(navController, isBottomBarVisible) },
                                    onError = { onError(it, context) },
                                    true
                                )
                                viewmodel.fetchAdminStatus()
                                navController.navigate(NoteGraph.ADMIN_SCREEN)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Неверный логин или пароль",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }, colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)) {
                            Text("Войти", color = MaterialTheme.colorScheme.tertiary)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAdminDialog.value = false }, colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)) {
                            Text("Отмена", color = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                )
            }

        }
    }
}

fun signInAnonymously(onSuccess: () -> Unit, onError: (String) -> Unit, isAdmin: Boolean) {
    FirebaseAuth.getInstance()
        .signInAnonymously()
        .addOnCompleteListener { task ->
            val db = Firebase.firestore
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val userMap = mapOf(
                    "uid" to it.uid,
                    "email" to it.email,
                    "isAnonymous" to it.isAnonymous,
                    "timestamp" to System.currentTimeMillis(),
                    "isAdmin" to isAdmin
                )
                db.collection("users").document(it.uid).set(userMap)
            }
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.message ?: "Ошибка авторизации")
            }
        }
}

fun onSuccess(navController: NavController, isBottomBarVisible: MutableState<Boolean>) {
    isBottomBarVisible.value = true
    navController.navigate(NoteGraph.MAIN_SCREEN)
}

fun onError(error: String, context: Context) {
    Toast.makeText(context, "Ошибка входа: $error", Toast.LENGTH_LONG).show()
}
