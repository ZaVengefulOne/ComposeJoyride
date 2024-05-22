package com.example.composejoyride.ui.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.data.utils.TopicItem
import com.example.composejoyride.ui.theme.Dimens
import org.jsoup.Jsoup
import org.jsoup.select.NodeFilter
import kotlin.concurrent.timerTask

@Composable
fun ListLib(navController: NavController, preferences: SharedPreferences)
{
    val buttonColor = MaterialTheme.colorScheme.secondary
    val buttonText = Color.White
    val topicsAmount = remember{ mutableIntStateOf(1) }
    val topicsList = remember { mutableStateOf(listOf<String>()) }
    val topicsLinks = remember { mutableStateOf(listOf<String>())}
    val topics = remember { mutableStateOf( topicsList.value.zip(topicsLinks.value) {topic, link -> listOf(topic, link)})}
    val gfgThread = Thread {
        try {
            val document =
                Jsoup.connect("https://nsaturnia.ru/kak-pisat-stixi/")
                    .get()
            val rhyme = document.select("h3")
            val links = document.select("h3 > a")
            topicsList.value = rhyme.map { it.text().toString() }.dropLast(1)
            topicsLinks.value = links.map {it.attr("href").toString()}.dropLast(1)
            topics.value =  topicsList.value.zip(topicsLinks.value) {topic, link -> listOf(topic, link)}
        } catch (e: Exception) {
            topicsList.value = listOf("Ошибка! Отсутствует подключение к сети!")
        }
    }
    gfgThread.start()
    val searchText = rememberSaveable { mutableStateOf("") }
    val filteredTopicsList = rememberSaveable { mutableStateOf(topics.value) }
    val localItems = mutableSetOf<String>()
    val trailingIconView = @Composable {
        IconButton(onClick = {
            searchText.value = ""
            filteredTopicsList.value = topics.value
        }) {
            Icon(Icons.Filled.Close, contentDescription = "Close Button", modifier = Modifier.size(25.dp), tint = Color.Black)
        }
    }
    val expanded = remember{ mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = {
                    searchText.value = it
                    //expanded.value = false
                },
                modifier = Modifier.padding(16.dp),
                placeholder = {
                    Text(
                        "Поиск...",
                        modifier = Modifier.clickable { expanded.value = true },
                        color = Color.Black,
                        fontFamily = CustomFontFamily
                    )
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (searchText.value.isEmpty()) {
                            filteredTopicsList.value = topics.value
                        } else {
                            filteredTopicsList.value = topics.value.filter {
                                it[0].contains(searchText.value, true)
                            }
                            saveSearchHistory(searchText.value, preferences)
                        }
                    }
                ),
                trailingIcon = if (searchText.value.isNotEmpty()) trailingIconView else null
            )
            DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                localItems.forEach { item ->
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
                items(filteredTopicsList.value.ifEmpty { topics.value }) { topicItem ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                preferences
                                    .edit()
                                    .putString(
                                        "topicURL",
                                        topicItem[1]
                                    )
                                    .apply()
                                navController.navigate("topic")
                            }, elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        ),  border = BorderStroke(1.dp, Color.Black)
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

private fun saveSearchHistory(query: String, sharedPreferences: SharedPreferences) {
    val historySet =
        sharedPreferences.getStringSet("search_key", mutableSetOf())?.toMutableSet()
            ?: mutableSetOf()
    if (historySet.contains(query)) {
        historySet.remove(query)
    }
    historySet.add(query)
    if (historySet.size > 10) {
        val iterator = historySet.iterator()
        for (i in 1..historySet.size - 10) {
            iterator.next()
            iterator.remove()
        }
    }
    sharedPreferences.edit {
        putStringSet("search_history", historySet)
    }
}