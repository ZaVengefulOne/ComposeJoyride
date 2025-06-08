package com.example.composejoyride.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.composables.RichTextFormattingToolbar
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.viewModels.NoteViewModel
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor

@OptIn(ExperimentalMaterial3Api::class)
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

    val richState = rememberRichTextState()
    LaunchedEffect(Unit) {
        richState.setHtml(note.note_content_html)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            OutlinedButton(
                onClick = {
                    noteViewModel.updateNoteText(richState.toHtml())
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
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor =
                        MaterialTheme.colorScheme.error
                ),
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }
        }


        OutlinedTextField(
            value = note.note_name,
            onValueChange = { noteViewModel.updateNoteName(it) },
            label = { Text(stringResource(R.string.newNote)) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { if (it.isFocused) isBottomBarVisible.value = false },
            textStyle = TextStyle(fontFamily = TheFont, fontSize = 22.sp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                isBottomBarVisible.value = true
                keyboardController?.hide()
            })
        )

        RichTextFormattingToolbar(richState)
        OutlinedRichTextEditor(
            state = richState,
            textStyle = TextStyle(fontFamily = TheFont, fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = Dimens.paddingMedium)
                .background(MaterialTheme.colorScheme.background)
                .onFocusChanged {
                    if (it.isFocused) isBottomBarVisible.value = false
                },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Default
                ),
            placeholder = {
                Text(
                    stringResource(R.string.enter_note_text),
                    style = TextStyle(
                        fontFamily = TheFont,
                        fontSize = 18.sp
                    )
                )
            },
        )

        BackHandler {
            isBottomBarVisible.value = true
            noteViewModel.updateNote()
        }
    }
}

