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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.ui.theme.Dimens
import org.jsoup.Jsoup

@Composable
fun AOTD() {
    val messageTitle = rememberSaveable{ mutableStateOf("") }
    val message = rememberSaveable{ mutableStateOf("") }
//    val scrollState = rememberScrollState()
//    val coroutineScope = rememberCoroutineScope()
//    val context = LocalContext.current
//    val buttonColor = MaterialTheme.colorScheme.secondary
    val buttonText = MaterialTheme.colorScheme.tertiary
    var isLoaded by rememberSaveable { mutableStateOf(false) }
    val errNameText = stringResource(id = R.string.error_name_not_found)
    val errTopicText = stringResource(id = R.string.error_topic_not_found)
    val showPB = rememberSaveable { mutableStateOf(false) }
    val topicsLinks = rememberSaveable { mutableStateOf(listOf<String>())}
    val getLinksThread = Thread {
        try {
            val document =
                Jsoup.connect("https://nsaturnia.ru/kak-pisat-stixi/")
                    .get()
            val rhyme = document.select("h3")
            val links = document.select("h3 > a")
            topicsLinks.value = links.map {it.attr("href").toString()}.dropLast(1)
        } catch (e: Exception) {
            topicsLinks.value = listOf("Ошибка! Отсутствует подключение к сети!")
        }
    }
    getLinksThread.start()
    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState(), reverseScrolling = true),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            if (!isLoaded) {
                Text(
                    text = stringResource(id = R.string.press_the_button),
                    modifier = Modifier
                        .padding(Dimens.paddingMedium)
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    color = buttonText,
                    textAlign = TextAlign.Center,
                    fontFamily = CustomFontFamily,
                    fontSize = 22.sp,
                )
            }
        }
        Text(
            text = messageTitle.value,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = CustomFontFamily,
            fontSize = 28.sp,
            color = buttonText
        )
        Text(
            text = message.value,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = CustomFontFamily,
            fontSize = 20.sp,
            color = buttonText
        )
        Box (modifier = Modifier.fillMaxWidth()) {
            if (!isLoaded) {
                OutlinedIconButton(onClick = {
                    showPB.value = true
                    val gfgThread = Thread {
                        try {
                            val document =
                                Jsoup.connect(topicsLinks.value.random())
                                    .get()
                            val titletext = document.title()
                            messageTitle.value = titletext
                            message.value = document.select("article").text()
                            isLoaded = true
                            showPB.value = false

                        } catch (e: Exception) {
                            messageTitle.value = errNameText
                            message.value = errTopicText
                        }
                    }
                    gfgThread.start()},
                    modifier= Modifier
                        .size(100.dp)
                        .padding(10.dp).align(Alignment.Center),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                ) {
                    androidx.compose.material.Icon(painter = painterResource(id = R.drawable.baseline_download_24), contentDescription = "content description", tint = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
        if (showPB.value){
            CircularProgressIndicator()
        }
    }
}