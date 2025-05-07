package com.example.composejoyride.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.ttFamily
import com.example.composejoyride.ui.viewModels.AOTDViewModel
import org.jsoup.Jsoup

@Composable
fun AOTD(navController: NavController) {

    val viewModel = sharedViewModel<AOTDViewModel>(navController)

    val articleName = viewModel.randomArcticleName.collectAsState().value
    val articleText = viewModel.randomArcticleText.collectAsState().value
    val isLoaded = viewModel.isLoaded.collectAsState().value
    val showPB = viewModel.showPB.collectAsState().value

    val buttonText = MaterialTheme.colorScheme.tertiary

    val scrollState = rememberScrollState()
    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState, reverseScrolling = false),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
            if (!isLoaded) {
                Text(
                    text = stringResource(id = R.string.press_the_button),
                    modifier = Modifier
                        .padding(Dimens.paddingMedium),
                    color = buttonText,
                    textAlign = TextAlign.Center,
                    fontFamily = ttFamily,
                    fontSize = 22.sp,
                )
                OutlinedIconButton(onClick = { viewModel.getRandomArticle() },
                    modifier= Modifier
                        .size(100.dp)
                        .padding(10.dp),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                ) {
                    Icon(Icons.Filled.Download, contentDescription = "content description",
                        tint = MaterialTheme.colorScheme.tertiary)
                }
            }
        else {
            Text(
                text = articleName,
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
        }
        if (showPB){
            CircularProgressIndicator()
        }
    }
}