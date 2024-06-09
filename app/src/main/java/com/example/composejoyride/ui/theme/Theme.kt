package com.example.composejoyride.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

var LocalTheme = mutableStateOf(false)
private val DarkColorScheme = darkColorScheme(
    primary = Dark,
    secondary = DarkCyan,
    tertiary = White,
    background = DarkerCyan
)
private val LightColorScheme = lightColorScheme(
    primary = SlavaPrimary,
    secondary = SlavaSecondary,
    tertiary = SlavaTetrtiary,
    background = SlavaBackground
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