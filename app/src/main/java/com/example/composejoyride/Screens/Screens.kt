package com.example.composejoyride.Screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun Main()  {
    val CustomFontFamily = FontFamily(Font(R.font.ds_moster))
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(painter = painterResource(id = R.drawable.alfa_logo), contentDescription = "App's logo", modifier = Modifier.fillMaxWidth())
        Text(
            text = "Привет и добро пожаловать в Poetry Helper v2.0!",
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            color = Color.Cyan,
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
            color = Color.Cyan,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontSize = 22.sp,
        )
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Cyan),
            modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_library_books_24), contentDescription = "Rhyme Button")
            Text(text = "Генератор рифм",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.ds_moster))
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Cyan),
            modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_menu_book_24), contentDescription = "Library Button")
            Text(text = "Библиотека статей",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.ds_moster))
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Cyan),
            modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_notes_24), contentDescription = "Notes Button")
            Text(text = "Заметки",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.ds_moster))
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Cyan),
            modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_settings_24), contentDescription = "Settings Button")
            Text(text = "Настройки",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.ds_moster))
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Cyan),
            modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_account_circle_24), contentDescription = "Account Button")
            Text(text = "Аккаунт",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.ds_moster))
            )
        }
    }
}

@Composable
fun Library() {
    val CustomFontFamily = FontFamily(Font(R.font.ds_moster))
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
            colors = ButtonDefaults.buttonColors(Color.Cyan),
            modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_download_24), contentDescription = "Article Download Button")
            Text(text = "Получить статью дня",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.ds_moster)),
                color = Color.Black)
        }
        Text(text = messageTitle.value, modifier = Modifier.fillMaxWidth(), fontFamily = FontFamily(Font(R.font.ds_moster)), fontSize = 28.sp, color = Color.White)
        Text(text = message.value, modifier = Modifier.fillMaxWidth(), fontFamily = FontFamily(Font(R.font.ds_moster)), fontSize = 20.sp, color = Color.White)
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
            .fillMaxWidth(), color = Color.Cyan, fontFamily = FontFamily(Font(R.font.ds_moster)), fontSize = 28.sp)
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
            IconButton(
                onClick = { message.intValue += 1 },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(128.dp),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C27B0))
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Add",
                    tint = Color.Cyan
                )
            }
        }

        items(message.intValue) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_notes_24),
                    contentDescription = "Article Icon",
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Cyan),
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Название статьи. Всего статей:${message.intValue}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.ds_moster))
                    )
                    Text(
                        text = "Первые строки статьи",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.ds_moster)),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
