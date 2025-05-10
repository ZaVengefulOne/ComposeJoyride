package com.example.composejoyride

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.ui.screens.AOTD
import com.example.composejoyride.ui.screens.Library
import com.example.composejoyride.ui.screens.Main
import com.example.composejoyride.ui.screens.Note
import com.example.composejoyride.ui.screens.Notes
import com.example.composejoyride.ui.screens.RhymeScreen
import com.example.composejoyride.ui.screens.Settings
import com.example.composejoyride.ui.screens.ArticleScreen
import com.example.composejoyride.ui.theme.ComposeJoyrideTheme
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
                    val isBottomBarVisible = remember { mutableStateOf(true) } // на будущее
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

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    preferences: SharedPreferences,
    bottomBarVisibility: MutableState<Boolean>
) {

    NavHost(
        navController = navController,
        startDestination = NoteGraph.MAIN_SCREEN,
        modifier = Modifier
            .padding(paddingValues = padding)
            .background(colorScheme.background),
        builder = {

            composable(NoteGraph.MAIN_SCREEN) {
                Main(navController, preferences)
            }

            composable(NoteGraph.AOTD_SCREEN) {
                AOTD(navController)
            }

            composable(NoteGraph.GENERATOR_SCREEN) {
                RhymeScreen(navController, bottomBarVisibility
                )
            }

            composable(NoteGraph.LIBRARY_SCREEN) {
                Library(navController,preferences, bottomBarVisibility)
            }
            composable(NoteGraph.SETTINGS_SCREEN) {
                Settings(navController, preferences)
            }
            composable(NoteGraph.NOTES_SCREEN) {
                Notes(navController)
            }
            composable(NoteGraph.ARTICLE_SCREEN){
                ArticleScreen(navController)
            }
            composable(NoteGraph.NOTE_SCREEN){
                Note(navController, {navController.navigate(NoteGraph.NOTES_SCREEN)}, bottomBarVisibility)
            }
        })

}


@Composable
fun BottomNavigationBar(navController: NavHostController, visibility: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = visibility.value,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        Surface(
            tonalElevation = 8.dp, // Подъём над фоном
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomStart = 24.dp,
                bottomEnd = 24.dp
            ), // Округление краёв
            shadowElevation = 8.dp, // Тень
            color = colorScheme.surface,
            modifier = Modifier.background(color = colorScheme.background)
        ) {
            BottomNavigation(
                elevation = 5.dp,
                backgroundColor = colorScheme.secondary
            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val currentRoute = navBackStackEntry?.destination?.route

                Constants.BottomNavItems.forEach { navItem ->

                    BottomNavigationItem(

                        selected = currentRoute == navItem.route,

                        onClick = {
                            navController.navigate(navItem.route)
                        },

                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },

                        label = {
                            Text(text = navItem.label, fontSize = 10.sp)
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }
    }
}