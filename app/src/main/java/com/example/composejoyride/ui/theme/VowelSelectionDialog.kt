package com.example.composejoyride.ui.theme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.composejoyride.data.utils.Constants

@Composable
fun VowelSelectionDialog(
    word: String,
    onDismissRequest: () -> Unit,
    onVowelSelected: (vowelIndex: Int) -> Unit
) {
    val vowelPositions = word.withIndex()
        .filter { it.value in Constants.vowels }
        .map { it.index }.reversed()

    Log.d("Гласные", vowelPositions.toString())

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .wrapContentHeight()
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "В слове, которое вы ввели, ${vowelPositions.size} гласных.\n" +
                            "Выберите, на какую поставить ударение (справа налево).",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontFamily = ttFamily
                )

                LazyRow(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    itemsIndexed(vowelPositions) { index, position ->
                        Button(
                            onClick = {
                                onVowelSelected(index)
                                onDismissRequest()
                            },
                            shape = MaterialTheme.shapes.small,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                        ) {
                            Text(
                                text = word[position].toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary,
                                fontFamily = ttFamily
                            )
                        }
                    }
                }
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Отмена",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontFamily = ttFamily)
                }
            }
        }
    }
}