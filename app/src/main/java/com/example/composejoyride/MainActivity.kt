package com.example.composejoyride

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.ui.theme.ComposeJoyrideTheme
import com.example.composejoyride.ui.theme.composables.BottomNavigationBar
import com.example.composejoyride.ui.theme.composables.NavHostContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        setContent {
            val sharedPrefs = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE)
                ComposeJoyrideTheme {
                    val navController = rememberNavController()
                    val isBottomBarVisible = remember { mutableStateOf(true) }
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colorScheme.background
                    ) {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationBar(navController = navController,
                                    visibility = isBottomBarVisible)
                            }, content = { padding ->
                                NavHostContainer(
                                    navController = navController,
                                    padding = padding,
                                    preferences = sharedPrefs,
                                    bottomBarVisibility = isBottomBarVisible
                                )
                            }
                        )
                    }
                }


        }
    }
}