package com.example.composejoyride.ui.screens

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.ttFamily
import com.example.composejoyride.ui.viewModels.ArticleViewModel
import com.example.composejoyride.ui.viewModels.LibraryViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Library(navController: NavController, preferences: SharedPreferences, isBottomBarVisible: MutableState<Boolean>) {
    val viewModel: LibraryViewModel = sharedViewModel(navController)
    val articleViewModel: ArticleViewModel = sharedViewModel(navController)
    viewModel.getArticles()

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(imeVisible) {
        isBottomBarVisible.value = !imeVisible
    }


    val buttonColor = MaterialTheme.colorScheme.secondary
    val buttonText = MaterialTheme.colorScheme.tertiary
    val topics = viewModel.articleItems.collectAsState().value
    val isLoaded = viewModel.isLoaded.collectAsState().value

    val searchText = rememberSaveable { mutableStateOf("") }
    val filteredTopicsList = rememberSaveable { mutableStateOf(topics) }
    val localItems = rememberSaveable {
        mutableStateOf(
            preferences.getStringSet(Constants.SEARCH_KEY, mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()
        )
    }
    val expanded = rememberSaveable { mutableStateOf(false) }
    val trailingIconView = @Composable {
        if (searchText.value.isNotEmpty()) {
            IconButton(onClick = {
                searchText.value = ""
                filteredTopicsList.value = topics
            }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close Button",
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            IconButton(
                onClick = { expanded.value = !expanded.value }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isLoaded) {
                CircularProgressIndicator()
            } else {
            Row {
                OutlinedTextField(
                    value = searchText.value,
                    onValueChange = {
                        searchText.value = it
                    },
                    modifier = Modifier.padding(16.dp)
                        .onFocusChanged { if (it.isFocused) isBottomBarVisible.value = false },
                    placeholder = {
                        Text(
                            "Поиск...",
                            modifier = Modifier.clickable { expanded.value = true },
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = ttFamily
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (searchText.value.isEmpty()) {
                                filteredTopicsList.value = topics
                            } else {
                                filteredTopicsList.value = topics.filter {
                                    it[0].contains(searchText.value, true)
                                }
                                viewModel.saveSearchHistory(searchText.value, preferences)
                                localItems.value =
                                    preferences.getStringSet(Constants.SEARCH_KEY, mutableSetOf())
                                        ?.toMutableSet()
                                        ?: mutableSetOf()
                            }
                            isBottomBarVisible.value = true
                            keyboardController?.hide()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = trailingIconView
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }) {
                    localItems.value.forEach { item ->
                        DropdownMenuItem(text = {
                            Text(text = item)
                        }, onClick = {
                            searchText.value = item
                            expanded.value = false
                        })
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                content = {
                    items(filteredTopicsList.value.ifEmpty { topics }) { topicItem ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable {
                                    articleViewModel.getArticle(topicItem[1])
                                    navController.navigate(NoteGraph.TOPIC_SCREEN)
                                }, elevation = 12.dp,
                            backgroundColor = MaterialTheme.colorScheme.secondary,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Row {
                                Image(
                                    painter = painterResource(R.drawable.baseline_notes_24),
                                    contentDescription = "Article Icon",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .background(buttonColor),
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(Dimens.paddingMedium)
                                        .align(Alignment.CenterVertically),
                                    text = topicItem[0],
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = buttonText,
                                    fontFamily = CustomFontFamily,
                                    textAlign = TextAlign.Start
                                )
                            }

                        }
                    }
                }
            )
        }
    }
}



