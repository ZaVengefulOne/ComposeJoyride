package com.example.composejoyride.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.TheFont
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    val font = TheFont
    val textColor = MaterialTheme.colorScheme.tertiary
    val user = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current
    Scaffold (
        topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Аккаунт",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                        navController.navigate(NoteGraph.MAIN_SCREEN)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }

            }
        )
    }) { padding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .padding(start = Dimens.paddingMedium, top = Dimens.paddingMedium),
            text = if (user?.isAnonymous == true) "Добро пожаловать, гость!" else "Добро пожаловать, ${user?.displayName}",
            fontSize = 20.sp,
            color = textColor,
            fontFamily = TheFont,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .padding(start = Dimens.paddingMedium, top = Dimens.paddingMedium),
            text = if (user?.isAnonymous == true) "Вы вошли как гость" else "Ваш email: ${user?.email}",
            fontSize = 20.sp,
            color = textColor,
            fontFamily = TheFont,
            textAlign = TextAlign.Center
        )


        OutlinedButton(
            onClick = { onLogout(context, navController) },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Выйти", fontFamily = font, color = textColor)
        }
    }
        }
}

fun onLogout(context: Context, navController: NavController) {
    signOut(context = context) {
        navController.navigate(NoteGraph.AUTH_SCREEN) {
            popUpTo(NoteGraph.MAIN_SCREEN) { inclusive = true }
        }
    }
}

fun signOut(context: Context, onComplete: () -> Unit) {
    AuthUI.getInstance()
        .signOut(context)
        .addOnCompleteListener {
            onComplete()
        }
}
