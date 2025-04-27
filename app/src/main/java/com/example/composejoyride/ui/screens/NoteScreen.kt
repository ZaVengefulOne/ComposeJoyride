package com.example.composejoyride.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Typography
import com.example.composejoyride.ui.viewModels.NoteViewModel

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun Note(
    navController: NavController,
    onDone: () -> Unit,
    ) {
    val isKeyboardOpen = WindowInsets.isImeVisible
    val noteViewModel: NoteViewModel = sharedViewModel(navController)
    val note by noteViewModel.note.collectAsState()

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
                    onDone()
                },
                //modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Filled.Save, contentDescription = null)
            }

            OutlinedButton(
                onClick = {
                    noteViewModel.deleteNote()
                    onDone()
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                //modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }
        }


        // Заголовок
        OutlinedTextField(
            value = note.note_name ?: "",
            onValueChange = { noteViewModel.updateNoteName(it) },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.titleLarge
        )




        // Текст заметки
        BasicTextField(
            value = note.note_text ?: "",
            onValueChange = { noteViewModel.updateNoteText(it) },
            textStyle = Typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        )


        }
    }
