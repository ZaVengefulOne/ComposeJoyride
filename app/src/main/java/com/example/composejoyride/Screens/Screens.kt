package com.example.composejoyride.Screens

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.composejoyride.MainActivity
import com.example.composejoyride.R
import com.example.composejoyride.Utils.MyWorker
import com.example.composejoyride.ui.theme.Dimens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit
val CustomFontFamily = FontFamily(Font(R.font.tippytoesbold))
@Composable
fun Main()  {

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
//        Image(painter = painterResource(id = R.drawable.alfa_logo), contentDescription = "App's logo", modifier = Modifier.fillMaxWidth())
        Text(
            text = "Привет и добро пожаловать в Poetry Helper v2.0!",
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontSize = 22.sp,
        )
        Text(
            text = "Данное приложение призвано помочь начинающим поэтам," +
                    " стихотворцам и прочим заинтересованным в стихосложении людям.",
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontSize = 22.sp,
        )
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Icon(painter = painterResource(R.drawable.baseline_library_books_24), contentDescription = "Rhyme Button")
            Text(text = "Генератор рифм",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Icon(painter = painterResource(R.drawable.baseline_menu_book_24), contentDescription = "Library Button")
            Text(text = "Библиотека статей",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Icon(painter = painterResource(R.drawable.baseline_notes_24), contentDescription = "Notes Button")
            Text(text = "Заметки",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Icon(painter = painterResource(R.drawable.baseline_settings_24), contentDescription = "Settings Button")
            Text(text = "Настройки",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Icon(painter = painterResource(R.drawable.baseline_account_circle_24), contentDescription = "Account Button")
            Text(text = "Аккаунт",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun Library() {
    val messageTitle = remember{ mutableStateOf("") }
    val message = remember{ mutableStateOf("") }
    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState(), reverseScrolling = true),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Нажмите на кнопку ниже, чтобы отобразить сегодняшнюю статью дня",
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            color = Color.Cyan,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontSize = 22.sp,
        )
        Button(
            onClick = {
                val gfgThread = Thread {
                    try {
                        val document =
                            Jsoup.connect("https://nsaturnia.ru/kak-pisat-stixi/vvodnaya-lekciya/")
                                .get();
                        val Titletext = document.title()
                        val ArticleText = document.getElementsByClass("article-container post").text()
                        messageTitle.value = Titletext
                        message.value = ArticleText
                    } catch (e: Exception) {
                        messageTitle.value = "Ошибка! Название статьи не найдено!"
                        message.value = "Ошибка! Статья не найдена!"
                    }
                }
                gfgThread.start()
                val  myWorkRequest = PeriodicWorkRequestBuilder<MyWorker>(10, TimeUnit.MINUTES, 10, TimeUnit.MINUTES).build()
                WorkManager.getInstance().enqueue(myWorkRequest)
            },
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_download_24), contentDescription = "Article Download Button")
            Text(text = "Получить статью дня",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = Color.Black)
        }
        Text(text = messageTitle.value, modifier = Modifier.fillMaxWidth(), fontFamily = CustomFontFamily, fontSize = 28.sp, color = Color.White)
        Text(text = message.value, modifier = Modifier.fillMaxWidth(), fontFamily = CustomFontFamily, fontSize = 20.sp, color = Color.White)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Rhyme()
{
    
    val message = remember{ mutableStateOf("") }
    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Введите ссылку на картинку в поле ниже:", modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.paddingMedium)
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(), color = Color.Cyan, fontFamily = CustomFontFamily, fontSize = 28.sp)
        TextField(value = message.value, onValueChange = {newText -> message.value = newText}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri) )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(message.value)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.baseline_done_24),
            contentDescription = "",
        )
    }
}

@Composable
fun ListLib()
{
    val message = remember{ mutableIntStateOf(0) }
    message.intValue = 1
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 48.dp)
    ) {
        item {
            Text(text = "Всего статей:${message.intValue} \nСписок статей: ", modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingMedium)
                .fillMaxWidth(), color = Color.Cyan, fontFamily = CustomFontFamily, fontSize = 28.sp, textAlign = TextAlign.Center)
            Row (modifier = Modifier.padding(start = 120.dp)) {

                IconButton(
                    onClick = { message.intValue += 1 },
                    modifier = Modifier
                        .size(64.dp),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C27B0))
                ) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Add",
                        tint = Color.Cyan
                    )
                }
                IconButton(
                    onClick = { message.intValue -= 1 },
                    modifier = Modifier
                        .size(64.dp),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C27B0))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Remove",
                        tint = Color.Cyan
                    )
                }
            }
        }

        items(message.intValue) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp), elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),  border = BorderStroke(3.dp, Color.White)
            ) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.baseline_notes_24),
                        contentDescription = "Article Icon",
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.Cyan),
                    )
                    Column {
                        Text(
                            text = "Название статьи",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.White,
                            fontFamily = CustomFontFamily,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Первые строки статьи",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.White,
                            fontFamily = CustomFontFamily,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
