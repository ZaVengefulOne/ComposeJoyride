package com.example.composejoyride.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.viewModels.SettingsViewModel
import androidx.core.content.edit
import com.example.composejoyride.ui.theme.Dimens.buttonWidth
import com.example.composejoyride.ui.theme.fontOptions

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Settings(navController: NavController, preferences: SharedPreferences) {
    val viewModel = sharedViewModel<SettingsViewModel>(navController)
    val isInfoOpen = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current


    var fontMenuExpanded by remember { mutableStateOf(false) }
    var selectedFont by remember { mutableStateOf(TheFont) }

    LocalTheme.value = preferences.getBoolean(Constants.EDIT_KEY, false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isInfoOpen.value) "О приложении" else "Настройки",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                        IconButton(onClick = {
                            if (isInfoOpen.value) {
                                isInfoOpen.value = false
                            } else {
                                navController.navigate(NoteGraph.MAIN_SCREEN)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад"
                            )
                        }

                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if (!isInfoOpen.value) {
                // Темная/Светлая тема
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = stringResource(R.string.theme_switcher),
                        fontFamily = selectedFont,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    OutlinedButton(
                        onClick = {
                            LocalTheme.value = !LocalTheme.value
                            preferences.edit { putBoolean(Constants.EDIT_KEY, LocalTheme.value) }
                                  },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                        shape = ButtonDefaults.squareShape,
                        modifier = Modifier.width(buttonWidth)
                    ) {
                        Text(
                            text = if (!LocalTheme.value) "Тёмная" else "Светлая",
                            fontFamily = selectedFont,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Выбор шрифта
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Выберите шрифт:",
                        fontFamily = selectedFont,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Box(Modifier.padding(start = Dimens.paddingLarge)) {
                        OutlinedButton(
                            onClick = { fontMenuExpanded = true },
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                            shape = ButtonDefaults.squareShape,
                            modifier = Modifier.width(buttonWidth)
                        ) {
                            Text(
                                text = fontOptions.firstOrNull { it.second == selectedFont }
                                    ?.first ?: TheFont.toString(),
                                fontFamily = selectedFont,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }

                        DropdownMenu(
                            expanded = fontMenuExpanded,
                            onDismissRequest = { fontMenuExpanded = false },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            shadowElevation = 5.dp
                        ) {
                            fontOptions.forEach { (label, font) ->
                                DropdownMenuItem(
                                    text = { Text(label, fontFamily = font) },
                                    onClick = {
                                        selectedFont = font
                                        fontMenuExpanded = false
                                        viewModel.setFont(font)
                                        navController.navigate(NoteGraph.MAIN_SCREEN)
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.tertiary)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Пункт "О приложении"
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = stringResource(R.string.about_app_button),
                        fontFamily = selectedFont,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    OutlinedButton(
                        onClick = { isInfoOpen.value = true},
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                        shape = ButtonDefaults.squareShape,
                        modifier = Modifier.width(buttonWidth)
                    ) {
                        Text(
                            text = "Тык сюда",
                            fontFamily = selectedFont,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }





            } else {
                // Экран "О приложении"
                Text(
                    text = stringResource(id = R.string.aboutApp),
                    textAlign = TextAlign.Start,
                    fontFamily = selectedFont,
                    modifier = Modifier.padding(Dimens.paddingMedium),
                    color = MaterialTheme.colorScheme.tertiary
                )

                Spacer(modifier = Modifier.weight(1f))

                OutlinedIconButton(
                    onClick = { isInfoOpen.value = false },
                    modifier = Modifier
                        .size(75.dp)
                        .padding(10.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Закрыть",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}
