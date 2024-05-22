package com.example.composejoyride

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composejoyride.ui.screens.Main
import com.example.composejoyride.ui.screens.Rhyme
import com.example.composejoyride.data.utils.Constants
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.composejoyride.data.utils.PREFERENCES

import com.example.composejoyride.ui.screens.AOTD
import com.example.composejoyride.ui.screens.ListLib
import com.example.composejoyride.ui.screens.Notes
import com.example.composejoyride.ui.screens.NotesSetup
import com.example.composejoyride.ui.screens.NotesViewModelFactory
import com.example.composejoyride.ui.screens.Settings
import com.example.composejoyride.ui.screens.Topic
import com.example.composejoyride.ui.theme.ComposeJoyrideTheme
import com.example.composejoyride.ui.viewModels.NotesViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPrefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                ComposeJoyrideTheme {
                    val navController = rememberNavController()
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colorScheme.background
                    ) {
                        // Scaffold Component
                        Scaffold(
                            // Bottom navigation
                            bottomBar = {
                                BottomNavigationBar(navController = navController)
                            }, content = { padding ->
                                // Navhost: where screens are placed
                                NavHostContainer(navController = navController, padding = padding, preferences = sharedPrefs, notesViewModel = LocalViewModelStoreOwner.current?.let {
                                    viewModel(
                                        it,
                                        "NotesViewModel",
                                        NotesViewModelFactory(
                                            LocalContext.current.applicationContext
                                                    as Application
                                        )
                                    )
                                })
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
    notesViewModel: NotesViewModel?
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

            // route : Home
            composable("main") {
                Main(navController = navController, preferences = preferences)
            }

            // route : search
            composable("aotd") {
                AOTD()
            }

            // route : profile
            composable("rhyme") {
                Rhyme()
            }

            composable("list") {
                ListLib(navController = navController,preferences = preferences)
            }
            composable("settings") {
                Settings(preferences)
            }
            composable("notes") {
                if (notesViewModel != null) {
                    NotesSetup(notesViewModel)
                }
            }
            composable("topic"){
                Topic(navController = navController, preferences = preferences)
            }
        })

}


@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation(

        // set background color
        backgroundColor = Color(0xFF028CA6)) {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->

            // Place the bottom nav items
            BottomNavigationItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                },

                // label
                label = {
                    Text(text = navItem.label, fontSize = 12.sp)
                },
                alwaysShowLabel = false
            )
        }
    }
}