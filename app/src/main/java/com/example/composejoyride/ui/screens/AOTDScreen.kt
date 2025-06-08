package com.example.composejoyride.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.viewModels.AOTDViewModel
import com.example.composejoyride.ui.viewModels.ArticleViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AOTD(navController: NavController) {

    val viewModel = sharedViewModel<AOTDViewModel>(navController)
    val articleViewModel = sharedViewModel<ArticleViewModel>(navController)

    val articleName = viewModel.randomArticleName.collectAsState().value
    val articleText = viewModel.randomArticleText.collectAsState().value
    val articleLink = viewModel.randomArticleLink.collectAsState().value

    val isLoaded = viewModel.isLoaded.collectAsState().value
    val showPB = viewModel.showPB.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    var showTopBar by remember { mutableStateOf(true) }
    var previousScrollPosition by remember { mutableIntStateOf(0) }
    var showScrollUpButton by remember { mutableStateOf(false) }
    val buttonText = MaterialTheme.colorScheme.tertiary
    val scrollState = rememberScrollState()

    if (!isLoaded && !showPB) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState, reverseScrolling = false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = R.string.press_the_button),
                modifier = Modifier
                    .padding(Dimens.paddingMedium),
                color = buttonText,
                textAlign = TextAlign.Center,
                fontFamily = TheFont,
                fontSize = 22.sp,
            )
            OutlinedIconButton(
                onClick = {
                    viewModel.getRandomArticle()
                },
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp),
                shape = CircleShape,
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
            ) {
                Icon(
                    Icons.Filled.Download, contentDescription = "Get Article",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    } else if (isLoaded && !showPB) {
        LaunchedEffect(scrollState.value) {
            showTopBar = scrollState.value <= previousScrollPosition || scrollState.value <= 20
            previousScrollPosition = scrollState.value
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
                                text = articleName,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 22.sp
                                ),
                                color = buttonText,
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                viewModel.getRandomArticle()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = "Перезапуск"
                                )
                            }
                        }
                    )
                }
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
                            contentDescription = "Up"
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
                        color = buttonText,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
        BackHandler {
            navController.navigate(NoteGraph.MAIN_SCREEN)
        }
    } else {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }
    }
}
