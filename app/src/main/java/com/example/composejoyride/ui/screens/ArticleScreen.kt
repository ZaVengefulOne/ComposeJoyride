package com.example.composejoyride.ui.screens

import android.content.SharedPreferences
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.viewModels.ArticleViewModel
import com.example.composejoyride.ui.viewModels.LibraryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Article(navController: NavController, preferences: SharedPreferences){

    val articleViewModel = sharedViewModel<ArticleViewModel>(navController)
    val libraryViewModel = sharedViewModel<LibraryViewModel>(navController)

    //val topicUrl = preferences.getString("topicURL", Constants.BASE_ARTICLES_URL)
    val articleTitle = articleViewModel.arcticleName.collectAsState().value
    val articleText = articleViewModel.arcticleText.collectAsState().value
    val textColor = MaterialTheme.colorScheme.tertiary
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var showTopBar by remember { mutableStateOf(true) }
    var previousScrollPosition by remember { mutableStateOf(0) }
    var showScrollUpButton by remember { mutableStateOf(false) }

    LaunchedEffect(scrollState.value) {
        // Определяем направление скролла
        showTopBar = scrollState.value <= previousScrollPosition || scrollState.value <= 20
        previousScrollPosition = scrollState.value

        // Показывать кнопку "вверх" если скроллим достаточно далеко
        showScrollUpButton = scrollState.value > 400
    }
    Scaffold(
    topBar = {
        AnimatedVisibility(
            visible = showTopBar,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it })
        ) {
        TopAppBar(
            title = {
                Text(
                text = articleTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 26.sp
                ),
                color = textColor,
            ) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(NoteGraph.LIBRARY_SCREEN)
                    articleViewModel.articleDrop()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            }
        )}
    },
    floatingActionButton = {
        AnimatedVisibility(
            visible = showScrollUpButton,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(0)
                    }
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.shadow(10.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Наверх"
                )
            }
        }
    }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = articleText,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 24.sp
                    ),
                    color = textColor,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
    BackHandler {
        navController.navigate(NoteGraph.LIBRARY_SCREEN)
        articleViewModel.articleDrop()
    }
}