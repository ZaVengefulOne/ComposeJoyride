package com.example.composejoyride

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
import com.example.composejoyride.screens.Library
import com.example.composejoyride.screens.Main
import com.example.composejoyride.screens.Rhyme
import com.example.composejoyride.Utils.Constants
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.composejoyride.screens.Account
import com.example.composejoyride.screens.ListLib
import com.example.composejoyride.screens.Notes
import com.example.composejoyride.screens.Settings
import com.example.composejoyride.ui.theme.ComposeJoyrideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeJoyrideTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    // Scaffold Component
                    Scaffold(
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            // Navhost: where screens are placed
                            NavHostContainer(navController = navController, padding = padding)
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
) {

    NavHost(
        navController = navController,
        // set the start destination as home
        startDestination = "main",

        // Set the padding provided by scaffold
        modifier = Modifier
            .padding(paddingValues = padding)
            .background(Color(0xff015B6C)),

        builder = {

            // route : Home
            composable("main") {
                Main(navController = navController)
            }

            // route : search
            composable("library") {
                Library()
            }

            // route : profile
            composable("rhyme") {
                Rhyme()
            }

            composable("list") {
                ListLib()
            }
            composable("settings") {
                Settings()
            }
            composable("account") {
                Account()
            }
            composable("notes") {
                Notes()
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