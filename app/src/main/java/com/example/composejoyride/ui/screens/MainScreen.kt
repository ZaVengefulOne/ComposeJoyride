package com.example.composejoyride.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.data.utils.EDIT_KEY
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.LocalTheme

@Composable
fun Main(navController: NavController, preferences: SharedPreferences)  {

    val buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
    val buttonText = MaterialTheme.colorScheme.tertiary
    LocalTheme.value = preferences.getBoolean(EDIT_KEY,false)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = stringResource(id = R.string.welcome),
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiary,
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
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontSize = 22.sp,
        )
        Button(
            onClick = { navController.navigate("rhyme") },
            colors = buttonColor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth().height(48.dp).padding(vertical = 4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_library_books_24),
                contentDescription = "Rhyme Button",
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = stringResource(id = R.string.generator),
                modifier = Modifier.fillMaxWidth().padding(start = Dimens.paddingLarge),
                fontFamily = CustomFontFamily,
                color = buttonText,
                fontSize = 22.sp
            )
        }
        Button(
            onClick = { navController.navigate("list") },
            colors = buttonColor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth().height(48.dp).padding(vertical = Dimens.paddingSmall)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_menu_book_24),
                contentDescription = "Library Button",
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = stringResource(id = R.string.library),
                modifier = Modifier.fillMaxWidth().padding(start = Dimens.paddingLarge),
                fontFamily = CustomFontFamily,
                color = buttonText,
                fontSize = 22.sp
            )
        }
        Button(
            onClick = { navController.navigate("notes") },
            colors = buttonColor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth().height(48.dp).padding(vertical = Dimens.paddingSmall)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_notes_24),
                contentDescription = "Notes Button",
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = stringResource(id = R.string.notes),
                modifier = Modifier.fillMaxWidth().padding(start = Dimens.paddingLarge),
                fontFamily = CustomFontFamily,
                color = buttonText,
                fontSize = 22.sp
            )
        }
        Button(
            onClick = { navController.navigate("settings") },
            colors = buttonColor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth().height(48.dp).padding(vertical = Dimens.paddingSmall)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_settings_24),
                contentDescription = "Settings Button",
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = stringResource(id = R.string.settings),
                modifier = Modifier.fillMaxWidth().padding(start = Dimens.paddingLarge),
                fontFamily = CustomFontFamily,
                color = buttonText,
                fontSize = 22.sp
            )
        }
    }
}