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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.composables.AlertDialog
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.viewModels.NoteViewModel
import com.example.composejoyride.ui.viewModels.NotesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation", "UnrememberedGetBackStackEntry")
@Composable
fun Notes(navController: NavController) {
    val notesViewModel: NotesViewModel = sharedViewModel(navController)
    val noteViewModel: NoteViewModel = sharedViewModel(navController)

    val allNotes by notesViewModel.allNotes.collectAsState()

    val openDeleteDialog = remember { mutableStateOf(false) }


    when {
        openDeleteDialog.value -> {
            AlertDialog(
                onDismissRequest = { openDeleteDialog.value = false },
                onConfirmation = {
                    openDeleteDialog.value = false
                    notesViewModel.clearNotes()
                },
                dialogTitle = stringResource(R.string.noteDeletion),
                dialogText = stringResource(R.string.noteDeletionRUSure),
                icon = Icons.Default.DeleteForever
            )
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Заметки",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = TheFont,
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
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
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
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clickable {
                                noteViewModel.setNote(index.id)
                                navController.navigate(NoteGraph.NOTE_SCREEN)
                            },
                        shape = CardDefaults.elevatedShape,
                        elevation = CardDefaults.cardElevation(12.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Icon(
                                Icons.AutoMirrored.Filled.Notes,
                                contentDescription = "Note",
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.padding(Dimens.paddingLarge)
                            )

                            Text(
                                text = index.note_name,
                                fontSize = 18.sp,
                                fontFamily = TheFont,
                                color = MaterialTheme.colorScheme.tertiary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )

                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedIconButton(
                    onClick = {
                        noteViewModel.createAndOpenNewNote()
                        navController.navigate(NoteGraph.NOTE_SCREEN)
                    },
                    modifier = Modifier
                        .size(75.dp)
                        .padding(10.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Note",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
                OutlinedIconButton(
                    onClick = {
                        openDeleteDialog.value = true
                    },
                    modifier = Modifier
                        .size(75.dp)
                        .padding(10.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(
                        Icons.Default.DeleteForever,
                        contentDescription = "Clear Notes",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}


