package com.example.composejoyride.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.composejoyride.Utils.DarkTheme

//val LocalTheme = compositionLocalOf { DarkTheme() }
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

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ComposeJoyrideTheme(
    // Dynamic color is available on Android 12+
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
)
{

    MaterialTheme(
        colorScheme = if (!LocalTheme.value) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}