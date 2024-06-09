package com.example.composejoyride.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.CustomFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun Topic(navController: NavController, preferences: SharedPreferences){
    val topicUrl = preferences.getString("topicURL", "https://nsaturnia.ru/kak-pisat-stixi/")
    val topicTitle = remember { mutableStateOf("")}
    val topicText = remember { mutableStateOf("")}
    val buttonText = MaterialTheme.colorScheme.tertiary
    val gfgThread = Thread {
        try {
            val document =
                Jsoup.connect(topicUrl!!)
                    .get()
            topicTitle.value = document.title()
            topicText.value = document.select("article").text()
        } catch (e: Exception) {
            topicTitle.value = "Ошибка! Отсутствует подключение к сети!"
            topicText.value = ""
        }
    }
    gfgThread.start()
    Column (modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState(), reverseScrolling = true)) {
        Text(
            text = topicTitle.value,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = CustomFontFamily,
            fontSize = 28.sp,
            color = buttonText
        )
        Text(
            text = topicText.value,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = CustomFontFamily,
            fontSize = 20.sp,
            color = buttonText
        )
        Button(
            onClick = { navController.navigate("list") },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(text = "Назад", color = MaterialTheme.colorScheme.tertiary,
                fontFamily = CustomFontFamily)
        }
    }
}