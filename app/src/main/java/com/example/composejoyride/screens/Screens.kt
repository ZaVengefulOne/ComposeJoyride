package com.example.composejoyride.screens
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composejoyride.R
import com.example.composejoyride.ui.theme.Dimens
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

val CustomFontFamily = FontFamily(Font(R.font.tippytoesbold))


@Composable
fun Main(navController: NavController)  {
    val button_color = colorScheme.secondary
    val buttoncolor = ButtonDefaults.buttonColors(button_color)
    val button_text = colorScheme.tertiary
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(40.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
//        Image(painter = painterResource(id = R.drawable.alfa_logo), contentDescription = "App's logo", modifier = Modifier.fillMaxWidth())
        Text(
            text = stringResource(id = R.string.welcome),
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            color = colorScheme.primary,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontSize = 22.sp,
        )
        Text(
            text = stringResource(id = R.string.entry),
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            color = colorScheme.primary,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontSize = 22.sp,
        )
        Button(
            onClick = { navController.navigate("rhyme") },
            colors = buttoncolor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_library_books_24), contentDescription = "Rhyme Button")
            Text(text = stringResource(id = R.string.generator),
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = button_text,
                fontSize = 22.sp
            )
        }
        Button(onClick = { navController.navigate("library") },
            colors = buttoncolor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_menu_book_24), contentDescription = "Library Button")
            Text(text = stringResource(id = R.string.library),
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = button_text,
                fontSize = 22.sp
            )
        }
        Button(onClick = { navController.navigate("notes") },
            colors = buttoncolor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_notes_24), contentDescription = "Notes Button")
            Text(text = stringResource(id = R.string.notes),
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = button_text,
                fontSize = 22.sp
            )
        }
        Button(onClick = { navController.navigate("settings") },
            colors = buttoncolor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_settings_24), contentDescription = "Settings Button")
            Text(text = stringResource(id = R.string.settings),
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = button_text,
                fontSize = 22.sp
            )
        }
        Button(onClick = { navController.navigate("account") },
            colors = buttoncolor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.baseline_account_circle_24), contentDescription = "Account Button")
            Text(text = stringResource(id = R.string.account),
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                color = button_text,
                fontSize = 22.sp
            )
        }
//        val checkedState = remember { mutableStateOf(true) }
//        Row{
//            Checkbox(
//                checked = AppCompatDelegate.setD,
//                onCheckedChange = { checkedState.value = it }
//            )
//            Text("Выбрано", fontSize = 28.sp, modifier = Modifier.padding(4.dp))
//        }
    }
}

@Composable
fun Library() {
    val messageTitle = rememberSaveable{ mutableStateOf("") }
    val message = rememberSaveable{ mutableStateOf("") }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val button_color = colorScheme.secondary
    val button_text = colorScheme.tertiary
    val errNameText = stringResource(id = R.string.error_name_not_found)
    val errTopicText = stringResource(id = R.string.error_topic_not_found)
    val linksList = arrayListOf("https://nsaturnia.ru/kak-pisat-stixi/vvodnaya-lekciya/","https://nsaturnia.ru/kak-pisat-stixi/chto-takoe-ritm/",
        "https://nsaturnia.ru/kak-pisat-stixi/dvuslozhnye-razmery/", "https://nsaturnia.ru/kak-pisat-stixi/chto-takoe-rifma/", "https://nsaturnia.ru/kak-pisat-stixi/vidy-rifmy/",
        "https://nsaturnia.ru/kak-pisat-stixi/sistemy-rifmovki/", "https://nsaturnia.ru/kak-pisat-stixi/vidy-sravnenij/")
    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState(), reverseScrolling = true),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
        Text(
            text = stringResource(id = R.string.press_the_button),
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.Center)
                .fillMaxWidth(),
            color = button_text,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontSize = 22.sp,
        )
        }
            Text(
                text = messageTitle.value,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                fontSize = 28.sp,
                color = button_text
            )
            Text(
                text = message.value,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                fontSize = 20.sp,
                color = button_text
            )
        Box (modifier = Modifier.fillMaxWidth()) {
            var isLoaded by remember { mutableStateOf(false) }
            if (!isLoaded) {
                ExtendedFloatingActionButton(
                    onClick = {
                        val gfgThread = Thread {
                            try {
                                val document =
                                    Jsoup.connect(linksList.random())
                                        .get()
                                val titletext = document.title()
                                val articleText =
                                    document.getElementsByClass("article-container post").text()
                                messageTitle.value = titletext
                                message.value = articleText

                            } catch (e: Exception) {
                                messageTitle.value = errNameText
                                message.value = errTopicText
                            }
                        }
                        gfgThread.start()
                        isLoaded = true
                    },
                    shape = CircleShape,
                    containerColor = colorScheme.secondary,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_download_24),
                        contentDescription = "Article Download Button"
                    )
                    Text(text = "Загрузить статью")
                }
//            Button(onClick = {
//                coroutineScope.launch {
//                    scrollState.scrollTo(0)
//                }
//                             }, modifier = Modifier
//                .align(Alignment.BottomStart)
//                .padding(horizontal = 50.dp),
//                colors = ButtonDefaults.buttonColors(button_color),
//                shape = CircleShape,)
//            {
//                Icon(
//                    painter = painterResource(R.drawable.baseline_keyboard_arrow_up_24),
//                    contentDescription = "Article Scroll Button"
//                )
//            }
            }
        }
    }
}


@Composable
fun Rhyme()
{
    
    val message = remember{ mutableStateOf("") }
    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.enter_rhyming_word), modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.paddingMedium)
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(), color = Color.White, fontFamily = CustomFontFamily, fontSize = 28.sp)
        TextField(value = message.value, modifier = Modifier.fillMaxWidth(), onValueChange = { newText -> message.value = newText}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListLib()
{
    val topics_amount = remember{ mutableIntStateOf(0) }
    val topicsList = remember { mutableStateOf(listOf(
        "Вводная лекция",
        "Что такое ритм?",
        "Двусложные и четырехсложные размеры в силлабо-тонике",
        "Трехсложные размеры в силлабо-тонике",
        "Работа с ритмом",
        "Чем отличается стих от прозы?",
        "Стихопроза: признаки и разновидности"
    )) }
    var searchText = rememberSaveable { mutableStateOf("") }
    val filteredTopicsList = rememberSaveable { mutableStateOf(topicsList.value) }
    val trailingIconView = @Composable {
        IconButton(onClick = {
            searchText.value = ""
            filteredTopicsList.value = topicsList.value
        }) {
            Icon(Icons.Filled.Close, contentDescription = "Close Button", modifier = Modifier.size(25.dp), tint = Color.Black)
        }
    }
//    var closeEnabled = false
    topics_amount.intValue = 1
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier.padding(16.dp),
                placeholder = { Text("Поиск...", color = Color.Black, fontFamily = CustomFontFamily) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (searchText.value.isEmpty()) {
                            filteredTopicsList.value = topicsList.value
//                            closeEnabled = false
                        } else {
                            filteredTopicsList.value = topicsList.value.filter {
                                it.contains(searchText.value, true)
                            }
//                            closeEnabled = true
                        }
                    }
                ),
                trailingIcon = if (!searchText.value.isEmpty()) trailingIconView else null
            )
        }
//        Text(text = "Количество статей: ${topics_amount.intValue}", color = Color.Black, fontFamily = CustomFontFamily)
//        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            content = {
                items(filteredTopicsList.value) { topicItem ->
                    TopicItem(topicItem)
                }
            }
        )
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun TopicItem(topicItem: String){
    val button_color = colorScheme.secondary
    val button_text = Color.White
        Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp), elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                ),  border = BorderStroke(1.dp, Color.Black)
            ) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.baseline_notes_24),
                        contentDescription = "Article Icon",
                        modifier = Modifier
                            .size(80.dp)
                            .background(button_color),
                    )
                    Column {
                        Text(
//                            modifier = Modifier.padding(Dimens.paddingMedium),
                            text = topicItem,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = button_text,
                            fontFamily = CustomFontFamily,
                            textAlign = TextAlign.Start
                        )
                        Text(
//                            modifier = Modifier.padding(Dimens.paddingMedium),
                            text = stringResource(id = R.string.first_words),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = button_text,
                            fontFamily = CustomFontFamily,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

@Composable
fun Notes()
{
    val button_color = colorScheme.secondary
    Image(
        painter = painterResource(R.drawable.baseline_done_24),
        contentDescription = "Article Icon",
        modifier = Modifier
            .size(80.dp)
            .background(button_color),
    )
}

@Composable
fun Account()
{
    val button_color = colorScheme.secondary
    Image(
        painter = painterResource(R.drawable.baseline_notes_24),
        contentDescription = "Article Icon",
        modifier = Modifier
            .size(80.dp)
            .background(button_color),
    )
}

@Composable
fun Settings()
{
    val button_color = colorScheme.secondary
    Image(
        painter = painterResource(R.drawable.baseline_download_24),
        contentDescription = "Article Icon",
        modifier = Modifier
            .size(80.dp)
            .background(button_color),
    )
}