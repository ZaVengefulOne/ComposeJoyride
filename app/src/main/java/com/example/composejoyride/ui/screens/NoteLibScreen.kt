package com.example.composejoyride.ui.screens

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.AlertDialog
import com.example.composejoyride.ui.viewModels.NoteViewModel
import com.example.composejoyride.ui.viewModels.NotesViewModel


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
    val selectedNoteId by rememberSaveable { mutableIntStateOf(0) }
    val openDeleteDialog = remember { mutableStateOf(false) }

    when {
        openDeleteDialog.value -> {
            AlertDialog(
                onDismissRequest = { openDeleteDialog.value = false },
                onConfirmation = {
                    openDeleteDialog.value = false
                    notesViewModel.clearNotes()
                },
                dialogTitle = "Удаление заметок",
                dialogText = "Вы уверены, что хотите очистить заметки?",
                icon = Icons.Default.DeleteForever
            )
        }
    }
        Column(modifier = Modifier.fillMaxSize()) {
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
                Row (
                    modifier = Modifier.align(Alignment.End).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
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
                }
                OutlinedIconButton(onClick = {
                    openDeleteDialog.value = true
                },
                    modifier= Modifier
                        .size(100.dp)
                        .padding(10.dp),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(Icons.Default.DeleteForever, contentDescription = "content description", tint = MaterialTheme.colorScheme.tertiary)
                }
                }
        }
}


