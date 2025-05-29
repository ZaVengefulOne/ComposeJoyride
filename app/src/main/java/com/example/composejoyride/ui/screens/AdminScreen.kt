package com.example.composejoyride.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.formatTimestamp
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.InfoRow
import com.example.composejoyride.ui.viewModels.SettingsViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navController: NavController,
    isBottomBarVisible: MutableState<Boolean>,
) {
    val users = remember { mutableStateListOf<Map<String, Any>>() }
    val viewModel: SettingsViewModel = sharedViewModel(navController)

    val context = LocalContext.current
    val textColor = MaterialTheme.colorScheme.tertiary
    isBottomBarVisible.value = true
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .get()
            .addOnSuccessListener { querySnapshot ->
                users.clear()
                users.addAll(
                    querySnapshot.documents
                        .mapNotNull { it.data }
                )
            }
            .addOnFailureListener {
                Toast.makeText(context, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Админ-панель",
                        fontFamily = TheFont,
                        fontSize = 24.sp,
                        color = textColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(NoteGraph.MAIN_SCREEN) {
                            popUpTo(0)
                        }
                    }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Зарегистрированные пользователи:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = TheFont,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.updateAdminStatus(false)
                        onLogout(
                            context, navController
                        )
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    androidx.compose.material3.Icon(
                        Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Выйти", fontFamily = TheFont, color = textColor)
                }
            }


            items(users.filter { user ->
                val isAdmin = user["isAdmin"] as? Boolean ?: false
                !isAdmin
            }) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        val uid = user["uid"] ?: "—"
                        val email = user["email"] ?: "Аноним"
                        val isGuest = user["isAnonymous"] ?: "?"
                        val date = formatTimestamp(user["timestamp"])

                        InfoRow("UID", uid.toString(), textColor)
                        InfoRow("Email", email.toString(), textColor)
                        InfoRow("Гость", isGuest.toString(), textColor)
                        InfoRow("Время входа", date, textColor)
                    }
                }
            }
        }
    }
}

