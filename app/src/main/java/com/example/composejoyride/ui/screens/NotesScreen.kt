package com.example.composejoyride.ui.screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.ui.viewModels.NotesViewModel

@Composable
fun NotesSetup (viewModel: NotesViewModel){
    val allNotes by viewModel.allNotes.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    Notes(allNotes = allNotes, searchResults = searchResults, viewModel = viewModel)
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun Notes(allNotes: List<Note>, searchResults: List<Note>, viewModel: NotesViewModel)
{
    var noteName by remember { mutableStateOf("")}
    var noteText by remember { mutableStateOf("") }
    var selectedNoteId by remember { mutableStateOf(0) }
    val onNoteNameChange = { text: String -> noteName = text }
    val onNoteTextChange = { text: String -> noteText = text }
    var isNoteMenuVisible by remember { mutableStateOf(false)}
    var openedForEditing by remember { mutableStateOf(false)}
        Column(modifier = Modifier.fillMaxSize()) {
            if (!isNoteMenuVisible) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(128.dp),
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        top = 16.dp,
                        end = 12.dp,
                        bottom = 16.dp
                    ), modifier = Modifier.weight(0.9f)
                ) {
                    items(allNotes) { index ->
                        Card(
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                                .clickable {
                                    isNoteMenuVisible = true
                                    openedForEditing = true
                                    noteName = index.note_name
                                    noteText = index.note_text
                                    selectedNoteId = index.id
                                },
                            elevation = 8.dp,
                        ) {
                            Text(
                                text = index.note_name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.tertiary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
                Row (modifier = Modifier.align(Alignment.End)){
                OutlinedIconButton(onClick = { isNoteMenuVisible = true},
                    modifier= Modifier
                        .size(100.dp)
                        .padding(10.dp),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "content description", tint = MaterialTheme.colorScheme.tertiary)
                }}
            } else {
                    TextField(
                        value = noteName,
                        onValueChange = { onNoteNameChange(it) },
                        placeholder = { Text("Название заметки") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(255,255,255,alpha = 0),
                            unfocusedIndicatorColor = Color(255,255,255,alpha = 0),
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background
                        ),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CustomFontFamily,
                            textAlign = TextAlign.Center
                        ), modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                    TextField(
                        value = noteText,
                        onValueChange = { onNoteTextChange(it) },
                        placeholder = {Text("Текст заметки")},
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(255,255,255,alpha = 0),
                            unfocusedIndicatorColor = Color(255,255,255,alpha = 0),
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background
                        ),
                        textStyle = TextStyle( color = MaterialTheme.colorScheme.tertiary,fontSize = 18.sp, fontFamily = CustomFontFamily),
                        maxLines = 50, modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState(), reverseScrolling = true)
                    )
                Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                    OutlinedIconButton(onClick = {
                        if (openedForEditing){
                        viewModel.updateNote(selectedNoteId,noteName,noteText)
                        openedForEditing = false
                        isNoteMenuVisible = false
                        noteName = ""
                        noteText = ""
                    } else {
                        viewModel.insertNote(Note(0, noteName, noteText))
                        isNoteMenuVisible = false
                        noteName = ""
                        noteText = ""
                    }},
                        modifier= Modifier
                            .size(100.dp)
                            .padding(10.dp),  //avoid the oval shape
                        shape = CircleShape,
                        border= BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "content description", tint = MaterialTheme.colorScheme.tertiary)
                    }
                    OutlinedIconButton(onClick = {
                        viewModel.deleteNote(noteName)
                        isNoteMenuVisible = false},
                        modifier= Modifier
                            .size(100.dp)
                            .padding(10.dp),
                        shape = CircleShape,
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "content description", tint = MaterialTheme.colorScheme.tertiary)
                    }
                    }
                }
            }
        }


class NotesViewModelFactory(val application: Application):
        ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(application) as T
    }
        }