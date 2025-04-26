package com.example.composejoyride.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.viewModels.NoteViewModel
import com.example.composejoyride.ui.viewModels.NotesViewModel
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation", "UnrememberedGetBackStackEntry")
@Composable
fun Notes(navController: NavController)
{
    val notesViewModel: NotesViewModel = sharedViewModel(navController)
    val noteViewModel: NoteViewModel = sharedViewModel(navController)

    val allNotes by notesViewModel.allNotes.collectAsState()
    val searchResults by notesViewModel.searchResults.collectAsState()

    notesViewModel.loadNotes()
    val newNote = Note(0, "Новая заметка", "")
    var noteName by rememberSaveable { mutableStateOf("")}
    var noteText by rememberSaveable { mutableStateOf("") }
    val onNoteNameChange = { text: String -> noteName = text }
    val onNoteTextChange = { text: String -> noteText = text }
    val selectedNoteId by rememberSaveable { mutableIntStateOf(0) }
    var isNoteMenuVisible by rememberSaveable { mutableStateOf(false)}
    var openedForEditing by rememberSaveable { mutableStateOf(false)}

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()


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
//                                    isNoteMenuVisible = true
//                                    openedForEditing = true
//                                    noteName = index.note_name
//                                    noteText = index.note_text
//                                    selectedNoteId = index.id
                                    Log.d("HASHCODE_VENGEFUL1", noteViewModel.hashCode().toString())
                                    noteViewModel.setNote(index.note_name)
                                    navController.navigate(NoteGraph.NOTE_SCREEN)
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
                OutlinedIconButton(onClick = {
                    noteViewModel.insertNote(newNote)
                    noteViewModel.setNote(newNote.note_name)
                    navController.navigate(NoteGraph.NOTE_SCREEN)
                },
                    modifier= Modifier
                        .size(100.dp)
                        .padding(10.dp),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "content description", tint = MaterialTheme.colorScheme.tertiary)
                }}
            } else {
                LaunchedEffect(scrollState) {
                    scope.launch {
                        scrollState.scrollTo(0)
                    }
                }
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
                            .verticalScroll(scrollState, reverseScrolling = false)
                    )
                Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                    OutlinedIconButton(onClick = {
                        if (openedForEditing){
                        noteViewModel.updateNote(selectedNoteId,noteName,noteText)
                        openedForEditing = false
                        isNoteMenuVisible = false
                        noteName = ""
                        noteText = ""
                    } else {
                        noteViewModel.insertNote(Note(0, noteName, noteText))
                        isNoteMenuVisible = false
                        noteName = ""
                        noteText = ""
                    }
                        notesViewModel.loadNotes()
                                                 },
                        modifier= Modifier
                            .size(100.dp)
                            .padding(10.dp),  //avoid the oval shape
                        shape = CircleShape,
                        border= BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "content description", tint = MaterialTheme.colorScheme.tertiary)
                    }
                    OutlinedIconButton(onClick = {
                        noteViewModel.deleteNote(noteName)
                        isNoteMenuVisible = false
                        notesViewModel.loadNotes()},
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

