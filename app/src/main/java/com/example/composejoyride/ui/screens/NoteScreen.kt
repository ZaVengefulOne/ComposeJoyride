package com.example.composejoyride.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Typography
import com.example.composejoyride.ui.theme.ttFamily
import com.example.composejoyride.ui.viewModels.NoteViewModel

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun Note(
    navController: NavController,
    onDone: () -> Unit,
    isBottomBarVisible: MutableState<Boolean>
    ) {
    val noteViewModel: NoteViewModel = sharedViewModel(navController)
    val note by noteViewModel.note.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        // Кнопки
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            OutlinedButton(
                onClick = {
                    noteViewModel.updateNote()
                    isBottomBarVisible.value = true
                    onDone()
                },
            ) {
                Icon(imageVector = Icons.Filled.Save, contentDescription = null)
            }

            OutlinedButton(
                onClick = {
                    noteViewModel.deleteNote()
                    isBottomBarVisible.value = true
                    onDone()
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor =
                    MaterialTheme.colorScheme.error),
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }
        }


        // Заголовок
        OutlinedTextField(
            value = note.note_name ?: "",
            onValueChange = { noteViewModel.updateNoteName(it) },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged { if (it.isFocused) isBottomBarVisible.value = false },
            textStyle = TextStyle(fontFamily = ttFamily, fontSize = 22.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                isBottomBarVisible.value = true
                keyboardController?.hide()
            })
        )




        // Текст заметки
        BasicTextField(
            value = note.note_text ?: "",
            onValueChange = { noteViewModel.updateNoteText(it) },
            textStyle = TextStyle(fontFamily = ttFamily, fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
                .onFocusChanged {
                    if (it.isFocused) isBottomBarVisible.value = false },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default),)

        BackHandler {
            isBottomBarVisible.value = true
            noteViewModel.updateNote()
        }
        }
    }
