package com.example.composejoyride.ui.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    val buttonColor = MaterialTheme.colorScheme.secondary
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
                            backgroundColor = MaterialTheme.colorScheme.tertiary,
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
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
                FloatingActionButton(onClick = {
                    isNoteMenuVisible = true
                },
                    shape = RoundedCornerShape(12.dp), containerColor = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(0.1f).fillMaxWidth())
                {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.White)
                }
            } else {
                    TextField(
                        value = noteName,
                        onValueChange = { onNoteNameChange(it) },
                        placeholder = { Text("Название заметки") },
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CustomFontFamily
                        ), modifier = Modifier
                            .weight(0.1f)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = noteText,
                        onValueChange = { onNoteTextChange(it) },
                        textStyle = TextStyle(fontSize = 18.sp, fontFamily = CustomFontFamily),
                        maxLines = 50, modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    )
                Row(modifier = Modifier.weight(0.05f)) {
                    Button(
                        onClick = {
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
                            }
                        },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(5f)
                    ) {
                        Text(text = "Готово", color = Color.White)
                    }
                    Button(
                        onClick = {
                            viewModel.deleteNote(noteName)
                            isNoteMenuVisible = false
                        },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(5f)
                    ) {
                        Text(text = "Удалить", color = Color.White)
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