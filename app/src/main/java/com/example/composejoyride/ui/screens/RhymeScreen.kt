package com.example.composejoyride.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.ui.theme.Dimens
import org.jsoup.Jsoup

@Composable
fun Rhyme()
{
    val context = LocalContext.current
    val message = rememberSaveable{ mutableStateOf("") }
    val result1 = rememberSaveable { mutableStateOf("") }
    //val result2 = remember { mutableStateOf("")}
    val resultArray = rememberSaveable { mutableStateOf(listOf<String>()) }
    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = message.value,
            onValueChange = { newText -> message.value = newText },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                val gfgThread = Thread {
                    try {
                        val document =
                            Jsoup.connect("https://rifme.net/r/${message.value}/0")
                                .get()
                        val rhyme = document.getElementsByClass("riLi")
                        resultArray.value = rhyme.map { it.text().toString() }
                        Log.d("VNIMANIE", resultArray.value.toString())
                    } catch (e: Exception) {
                        result1.value = "Ошибка!"
                    }
                }
                gfgThread.start()
            }), singleLine = true
        )
//        Button(
//            onClick = {
//
//            },
//            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
//            shape = RoundedCornerShape(12.dp),
//        ) {
//            Text(text = "Подобрать рифму", color = MaterialTheme.colorScheme.tertiary, fontFamily = CustomFontFamily, fontSize = 28.sp)
//        }
        Text(text = "Найдено слов: ${resultArray.value.size}", modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.paddingMedium)
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary, fontFamily = CustomFontFamily, fontSize = 28.sp, textAlign = TextAlign.Center)
        Log.d("VNIMANIE", resultArray.value.toString())
        LazyColumn (
            content = {
                items(resultArray.value) { rhymeItem ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    ),  border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Row {
                            Text(text = rhymeItem, color = MaterialTheme.colorScheme.tertiary, fontFamily = CustomFontFamily, fontSize = 28.sp, modifier = Modifier.align(
                                Alignment.CenterVertically).weight(0.9f).padding(start = 10.dp))
                            IconButton(onClick = {
                                val clipboardManager =
                                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                val clipData: ClipData = ClipData.newPlainText("text", rhymeItem)
                                clipboardManager.setPrimaryClip(clipData)
                                Toast.makeText(context, "Скопировано в буфер обмена!", Toast.LENGTH_LONG).show()
                            }, modifier = Modifier.weight(0.1f)) {
                                Icon(painter = painterResource(R.drawable.baseline_content_copy_24), contentDescription = null)
                            }
                        }

                    }
                }
            }, modifier = Modifier.fillMaxWidth()
        )
    }
}