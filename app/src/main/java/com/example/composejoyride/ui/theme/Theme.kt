package com.example.composejoyride.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

var LocalTheme = mutableStateOf(false)
private val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    secondary = DarkCyan,
    tertiary = Color.Black,
    background = DarkerCyan
)
private val LightColorScheme = lightColorScheme(
    primary = Color.LightGray,
    secondary = Cyan,
    tertiary = Color.Black,
    background = DarkCyan
)

@Composable
fun ComposeJoyrideTheme(
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
)
{

    MaterialTheme(
        colorScheme = if (!LocalTheme.value) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}