package com.example.composejoyride.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.VowelSelectionDialog
import com.example.composejoyride.ui.theme.ttFamily
import com.example.composejoyride.ui.viewModels.RhymeViewModel
import org.jsoup.Jsoup

@Composable
fun Rhyme(navController: NavController, isBottomBarVisible: MutableState<Boolean>)
{
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

    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            modifier = Modifier
                .onFocusChanged { if (it.isFocused) isBottomBarVisible.value = false },
            value = message,
            onValueChange = { newText -> viewModel.setInput(newText) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                isBottomBarVisible.value = true
                keyboardController?.hide()
                showDialog = true
                focusManager.clearFocus()
            }), singleLine = true
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

        Text(text = "Найдено слов: ${resultArray.size}", modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.paddingMedium)
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiary,
            fontFamily = ttFamily,
            fontSize = 28.sp,
            textAlign = TextAlign.Center)
        LazyColumn (
            content = {
                items(resultArray) { rhymeItem ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    ),  border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Row {
                            Text(text = rhymeItem,
                                color = MaterialTheme.colorScheme.tertiary,
                                fontFamily = ttFamily,
                                fontSize = 28.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(0.9f)
                                    .padding(start = 10.dp))
                            IconButton(onClick = { viewModel.copyToClipBoard(context, rhymeItem) },
                                modifier = Modifier.weight(0.1f)) {
                                Icon(Icons.Filled.ContentCopy,
                                    contentDescription = "Copy")
                            }
                        }

                    }
                }
            }, modifier = Modifier.fillMaxWidth()
        )
    }
}


