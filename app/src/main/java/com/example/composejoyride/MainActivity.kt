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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.composejoyride.ui.screens.Rhyme
import com.example.composejoyride.ui.screens.Settings
import com.example.composejoyride.ui.screens.Topic
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
                    var selectedRoute by rememberSaveable { mutableStateOf(NoteGraph.MAIN_SCREEN) }
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colorScheme.background
                    ) {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationBar(navController = navController)
                            }, content = { padding ->
                                NavHostContainer(navController = navController, padding = padding, preferences = sharedPrefs)
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
                AOTD()
            }

            composable(NoteGraph.GENERATOR_SCREEN) {
                Rhyme()
            }

            composable(NoteGraph.LIBRARY_SCREEN) {
                Library(navController,preferences)
            }
            composable(NoteGraph.SETTINGS_SCREEN) {
                Settings(preferences)
            }
            composable(NoteGraph.NOTES_SCREEN) {
                Notes(navController)
            }
            composable(NoteGraph.TOPIC_SCREEN){
                Topic(navController, preferences)
            }
            composable(NoteGraph.NOTE_SCREEN){
                Note(navController, {navController.navigate(NoteGraph.NOTES_SCREEN)})
            }
        })

}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Surface(
        tonalElevation = 8.dp, // Подъём над фоном
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 24.dp, bottomEnd = 24.dp), // Округление краёв
        shadowElevation = 8.dp, // Тень
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