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
import com.example.composejoyride.ui.viewModels.NoteViewModel

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun Note(
    navController: NavController,
    onDone: () -> Unit,
    ) {
    val noteViewModel: NoteViewModel = sharedViewModel(navController)

    val note by noteViewModel.note.collectAsState()
    val isKeyboardVisible = WindowInsets.isImeVisible

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                annotatedString = AnnotatedString(note.note_text),
                selection = TextRange(0)
            )
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp)
    ) {
        // Заголовок
        OutlinedTextField(
            value = note.note_name,
            onValueChange = { noteViewModel.updateNoteName(it) },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Панель редактирования шрифта
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { textFieldValue = applyStyle(textFieldValue, FontWeight.Bold) }) {
                Icon(Icons.Default.FormatBold, contentDescription = "Bold")
            }
            IconButton(onClick = {
                textFieldValue = applyStyle(textFieldValue, fontStyle = FontStyle.Italic)
            }) {
                Icon(Icons.Default.FormatItalic, contentDescription = "Italic")
            }
            IconButton(onClick = {
                textFieldValue =
                    applyStyle(textFieldValue, textDecoration = TextDecoration.Underline)
            }) {
                Icon(Icons.Default.FormatUnderlined, contentDescription = "Underline")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Поле текста
        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it.copy(annotatedString = it.annotatedString)
                noteViewModel.updateNoteText(it.annotatedString.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF5F5F5))
                .padding(12.dp),
            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
        )


        // Кнопки
        if (!isKeyboardVisible) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        noteViewModel.updateNote(note.id, note.note_name, note.note_text)
                        onDone()
                        keyboardController?.hide()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Сохранить")
                }

                OutlinedButton(
                    onClick = {
                        noteViewModel.deleteNote(note.note_name)
                        onDone()
                        keyboardController?.hide()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Удалить")
                }
            }
        }
    }
}

// Функция для применения стиля к выделенному тексту
private fun applyStyle(
    textFieldValue: TextFieldValue,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    textDecoration: TextDecoration? = null
): TextFieldValue {
    val selection = textFieldValue.selection
    if (selection.min == selection.max) return textFieldValue // Нет выделения

    val annotatedString = buildAnnotatedString {
        append(textFieldValue.text)
        if (fontWeight != null) {
            addStyle(SpanStyle(fontWeight = fontWeight), selection.start, selection.end)
        }
        if (fontStyle != null) {
            addStyle(SpanStyle(fontStyle = fontStyle), selection.start, selection.end)
        }
        if (textDecoration != null) {
            addStyle(SpanStyle(textDecoration = textDecoration), selection.start, selection.end)
        }
    }

    return textFieldValue.copy(annotatedString = annotatedString)
}