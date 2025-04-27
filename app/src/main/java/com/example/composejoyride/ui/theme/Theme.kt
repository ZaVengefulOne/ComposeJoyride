package com.example.composejoyride.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

var LocalTheme = mutableStateOf(false)
private val DarkColorScheme = darkColorScheme(
    primary = Dark,
    secondary = DarkCyan,
    tertiary = White,
    background = DarkerCyan,
    error = Red
)
private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = LightBackground,
    error = Red
)

@Composable
fun ComposeJoyrideTheme(
    content: @Composable () -> Unit
)
{

    MaterialTheme(
        colorScheme = if (!LocalTheme.value) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}