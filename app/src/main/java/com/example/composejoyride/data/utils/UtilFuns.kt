package com.example.composejoyride.data.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composejoyride.R
import com.example.composejoyride.ui.screens.Topic
import com.example.composejoyride.ui.theme.Dimens

@SuppressLint("SuspiciousIndentation")
@Composable
fun TopicItem(topicItem: String){
    val buttonColor = MaterialTheme.colorScheme.secondary
    val buttonText = Color.White
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
                    .background(buttonColor),
            )
                Text(
                    modifier = Modifier
                        .padding(Dimens.paddingMedium)
                        .align(Alignment.CenterVertically),
                    text = topicItem,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = buttonText,
                    fontFamily = CustomFontFamily,
                    textAlign = TextAlign.Start
                )
        }
    }
}