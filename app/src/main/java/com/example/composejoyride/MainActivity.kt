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
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.PREFERENCES
import com.example.composejoyride.ui.screens.AOTD
import com.example.composejoyride.ui.screens.Library
import com.example.composejoyride.ui.screens.Main
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
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        setContent {
            val sharedPrefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                ComposeJoyrideTheme {
                    val navController = rememberNavController()
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
        // set the start destination as home
        startDestination = "main",

        // Set the padding provided by scaffold
        modifier = Modifier
            .padding(paddingValues = padding)
            .background(colorScheme.background),

        builder = {

            composable("main") {
                Main(navController = navController, preferences = preferences)
            }

            composable("aotd") {
                AOTD()
            }

            composable("rhyme") {
                Rhyme()
            }

            composable("list") {
                Library(navController = navController, preferences = preferences)
            }
            composable("settings") {
                Settings(preferences)
            }
            composable("notes") {
                Notes()
            }
            composable("topic"){
                Topic(navController = navController, preferences = preferences)
            }
        })

}


@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation(

        backgroundColor = colorScheme.secondary) {

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