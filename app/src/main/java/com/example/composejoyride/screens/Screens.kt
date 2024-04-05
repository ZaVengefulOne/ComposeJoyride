package com.example.composejoyride.screens
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
val button_color = Color(0xFF028CA6)
val button_text = Color.White
@Composable
fun Main(navController: NavController)  {
    val context = LocalContext.current
    val buttoncolor = ButtonDefaults.buttonColors(button_color)
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
            color = Color(0xFFFFFFFF),
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
            color = Color.White,
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
            Text(text = stringResource(id = R.string.welcome),
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
    }
}

@Composable
fun Library() {
    val messageTitle = remember{ mutableStateOf("") }
    val message = remember{ mutableStateOf("") }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val errNameText = stringResource(id = R.string.error_name_not_found)
    val errTopicText = stringResource(id = R.string.error_topic_not_found)
    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState(), reverseScrolling = true),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(painter = painterResource(id = R.drawable.baseline_done_24), contentDescription = "for scrolling", modifier = Modifier.align(
                Alignment.BottomStart))
        Text(
            text = stringResource(id = R.string.press_the_button),
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.Center)
                .fillMaxWidth(),
            color = Color.White,
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
                color = Color.White
            )
            Text(
                text = message.value,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = CustomFontFamily,
                fontSize = 20.sp,
                color = Color.White
            )
        Box (modifier = Modifier.fillMaxWidth()){
            Button(
                onClick = {
                    val gfgThread = Thread {
                        try {
                            val document =
                                Jsoup.connect("https://nsaturnia.ru/kak-pisat-stixi/vvodnaya-lekciya/")
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
                },
                colors = ButtonDefaults.buttonColors(button_color),
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_download_24),
                    contentDescription = "Article Download Button"
                )
            }
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.scrollTo(0)
                    Toast.makeText(context,"Dostal!", Toast.LENGTH_LONG).show()
                }
                             }, modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 50.dp),
                colors = ButtonDefaults.buttonColors(button_color),
                shape = CircleShape,)
            {
                Icon(
                    painter = painterResource(R.drawable.baseline_keyboard_arrow_up_24),
                    contentDescription = "Article Scroll Button"
                )
            }
        }
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
                .fillMaxWidth(), color = Color.White, fontFamily = CustomFontFamily, fontSize = 28.sp, textAlign = TextAlign.Center)
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
                        tint = Color.White
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
                        tint = Color.White
                    )
                }
            }
        }

        items(message.intValue) { index ->
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
                            text = stringResource(id = R.string.topic_name),
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.White,
                            fontFamily = CustomFontFamily,
                            textAlign = TextAlign.Center
                        )
                        Text(
//                            modifier = Modifier.padding(Dimens.paddingMedium),
                            text = stringResource(id = R.string.first_words),
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

@Composable
fun Notes()
{
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
    Image(
        painter = painterResource(R.drawable.baseline_download_24),
        contentDescription = "Article Icon",
        modifier = Modifier
            .size(80.dp)
            .background(button_color),
    )
}