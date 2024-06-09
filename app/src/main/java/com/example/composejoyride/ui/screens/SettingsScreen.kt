package com.example.composejoyride.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.CustomFontFamily
import com.example.composejoyride.data.utils.EDIT_KEY
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.LocalTheme

@Composable
fun Settings(preferences: SharedPreferences)
{
    val isInfoOpen = remember { mutableStateOf(false)}
    val buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
    LocalTheme.value = preferences.getBoolean(EDIT_KEY,false)
    Column(modifier = Modifier.fillMaxSize()) {
        if(!isInfoOpen.value) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Переключатель темы: ", fontFamily = CustomFontFamily, color = MaterialTheme.colorScheme.tertiary)
                IconButton(onClick = {
                    LocalTheme.value = !LocalTheme.value
                    preferences.edit().putBoolean(EDIT_KEY, LocalTheme.value).apply()
                }) {
                    Icon(
                        painter = painterResource(if (!LocalTheme.value) R.drawable.baseline_dark_mode_24 else R.drawable.baseline_light_mode_24),
                        contentDescription = "theme switcher",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "О приложении: ", fontFamily = CustomFontFamily, color = MaterialTheme.colorScheme.tertiary)
                IconButton(onClick = { isInfoOpen.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_outline_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
        else{
            Text(text = stringResource(id = R.string.aboutApp), textAlign = TextAlign.Center, fontFamily = CustomFontFamily, modifier = Modifier.padding(
                Dimens.paddingMedium), color = MaterialTheme.colorScheme.tertiary)
            Button(onClick = { isInfoOpen.value = false }, colors = buttonColor,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()) {
                Text(text = "Назад", fontFamily = CustomFontFamily, color = MaterialTheme.colorScheme.tertiary)
            }
        }
    }
}