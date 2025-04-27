package com.example.composejoyride.ui.screens

import android.content.SharedPreferences
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.ttFamily
import com.example.composejoyride.ui.viewModels.ArticleViewModel
import com.example.composejoyride.ui.viewModels.LibraryViewModel

@Composable
fun Topic(navController: NavController, preferences: SharedPreferences){

    val articleViewModel = sharedViewModel<ArticleViewModel>(navController)
    val libraryViewModel = sharedViewModel<LibraryViewModel>(navController)

    //val topicUrl = preferences.getString("topicURL", Constants.BASE_ARTICLES_URL)
    val articleTitle = articleViewModel.arcticleName.collectAsState().value
    val articleText = articleViewModel.arcticleText.collectAsState().value
    val buttonText = MaterialTheme.colorScheme.tertiary

    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState(), reverseScrolling = false)) {
        Text(
            text = articleTitle,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = ttFamily,
            fontSize = 28.sp,
            color = buttonText
        )
        Text(
            text = articleText,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = ttFamily,
            fontSize = 20.sp,
            color = buttonText
        )
        Button(
            onClick = {
                navController.navigate(NoteGraph.LIBRARY_SCREEN)
                articleViewModel.articleDrop()
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(text = "Назад", color = MaterialTheme.colorScheme.tertiary,
                fontFamily = ttFamily)
        }
        BackHandler {
            navController.navigate(NoteGraph.LIBRARY_SCREEN)
            articleViewModel.articleDrop()
        }
    }
}