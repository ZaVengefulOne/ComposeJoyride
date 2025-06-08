package com.example.composejoyride.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.composables.VowelSelectionDialog
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.viewModels.RhymeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhymeScreen(navController: NavController, isBottomBarVisible: MutableState<Boolean>) {
    val context = LocalContext.current
    val viewModel: RhymeViewModel = sharedViewModel(navController)
    val message = viewModel.input.collectAsState().value
    val resultArray = viewModel.result.collectAsState().value
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(imeVisible) {
        isBottomBarVisible.value = !imeVisible
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Генератор рифм",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .onFocusChanged { isBottomBarVisible.value = !it.isFocused },
                value = message,
                onValueChange = { newText -> viewModel.setInput(newText) },
                placeholder = {
                    Text(
                        "Поиск...",
                        color = Color.Black,
                        fontFamily = TheFont
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    focusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        isBottomBarVisible.value = true
                        keyboardController?.hide()
                        showDialog = true
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Clear",
                        modifier = Modifier.clickable {
                            viewModel.setInput("")
                            isBottomBarVisible.value = true
                            keyboardController?.hide()
                        },
                        tint = Color.Black
                    )
                }
            )


            if (showDialog) {
                VowelSelectionDialog(
                    word = message,
                    onDismissRequest = { showDialog = false },
                    onVowelSelected = { index ->
                        selectedIndex = index
                        viewModel.findRhymes(index)
                    }
                )
            }

            Text(
                text = "Найдено слов: ${resultArray.size}", modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingMedium)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.tertiary,
                fontFamily = TheFont,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                content = {
                    items(resultArray) { rhymeItem ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp), elevation = CardDefaults.cardElevation(
                                defaultElevation = 12.dp
                            ), border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Row {
                                Text(
                                    text = rhymeItem,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = TheFont,
                                    fontSize = 28.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .weight(0.9f)
                                        .padding(start = 10.dp)
                                )
                                IconButton(
                                    onClick = { viewModel.copyToClipBoard(context, rhymeItem) },
                                    modifier = Modifier.weight(0.1f)
                                ) {
                                    Icon(
                                        Icons.Filled.ContentCopy,
                                        contentDescription = "Copy"
                                    )
                                }
                            }

                        }
                    }
                }, modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


