package com.example.composejoyride.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.LocalTheme
import com.example.composejoyride.ui.theme.schistFont
import com.example.composejoyride.ui.theme.tippyToesFont
import com.example.composejoyride.ui.theme.ttFamily
import com.example.composejoyride.ui.viewModels.SettingsViewModel
import androidx.core.content.edit

@Composable
fun Settings(navController: NavController, preferences: SharedPreferences)
{
    val viewModel = sharedViewModel<SettingsViewModel>(navController)
    val isInfoOpen = rememberSaveable { mutableStateOf(false)}
    LocalTheme.value = preferences.getBoolean(Constants.EDIT_KEY,false)
    key(ttFamily){
    Column(modifier = Modifier.fillMaxSize()) {
        if(!isInfoOpen.value) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = stringResource(R.string.theme_switcher), fontFamily = ttFamily, color = MaterialTheme.colorScheme.tertiary)
                IconButton(onClick = {
                    LocalTheme.value = !LocalTheme.value
                    preferences.edit { putBoolean(Constants.EDIT_KEY, LocalTheme.value) }
                }) {
                    Icon(
                        painter = painterResource(if (!LocalTheme.value) R.drawable.baseline_dark_mode_24 else R.drawable.baseline_light_mode_24),
                        contentDescription = "theme switcher",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = stringResource(R.string.about_app_button), fontFamily = ttFamily, color = MaterialTheme.colorScheme.tertiary)
                IconButton(onClick = { isInfoOpen.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_outline_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            Button(
                onClick = {
                    viewModel.setFont(schistFont)
                    navController.navigate(NoteGraph.MAIN_SCREEN)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(vertical = Dimens.paddingSmall)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_settings_24),
                    contentDescription = "Settings Button",
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = stringResource(R.string.that_font),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Dimens.paddingLarge),
                    fontFamily = schistFont,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 22.sp
                )
            }
            Button(
                onClick = {
                    viewModel.setFont(tippyToesFont)
                    navController.navigate(NoteGraph.MAIN_SCREEN)
                          },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(vertical = Dimens.paddingSmall)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_settings_24),
                    contentDescription = "Settings Button",
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = stringResource(R.string.this_font),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Dimens.paddingLarge),
                    fontFamily = tippyToesFont,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 22.sp
                )
            }
        }
        else{
            Text(text = stringResource(id = R.string.aboutApp), textAlign = TextAlign.Center, fontFamily = ttFamily, modifier = Modifier.padding(
                Dimens.paddingMedium), color = MaterialTheme.colorScheme.tertiary)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedIconButton(
                    onClick = { isInfoOpen.value = false },
                    modifier = Modifier
                        .size(100.dp)
                        .padding(10.dp),  //avoid the oval shape
                    shape = CircleShape,
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary),
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "content description",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}}