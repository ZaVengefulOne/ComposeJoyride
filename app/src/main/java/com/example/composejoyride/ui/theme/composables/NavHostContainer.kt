package com.example.composejoyride.ui.theme.composables

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.ui.screens.AOTD
import com.example.composejoyride.ui.screens.AdminScreen
import com.example.composejoyride.ui.screens.ArticleScreen
import com.example.composejoyride.ui.screens.AuthScreen
import com.example.composejoyride.ui.screens.Library
import com.example.composejoyride.ui.screens.Main
import com.example.composejoyride.ui.screens.Note
import com.example.composejoyride.ui.screens.Notes
import com.example.composejoyride.ui.screens.ProfileScreen
import com.example.composejoyride.ui.screens.RhymeScreen
import com.example.composejoyride.ui.screens.Settings
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    preferences: SharedPreferences,
    bottomBarVisibility: MutableState<Boolean>
) {

    val auth = FirebaseAuth.getInstance()
    val isAuthenticated = auth.currentUser != null

    NavHost(
        navController = navController,
        startDestination = NoteGraph.MAIN,
        route = NoteGraph.ROOT,
        modifier = Modifier
            .padding(paddingValues = padding)
            .background(colorScheme.background),
    ) {
        navigation(
            startDestination = if (isAuthenticated) NoteGraph.MAIN_SCREEN else NoteGraph.AUTH_SCREEN,
            route = NoteGraph.MAIN
        ) {
            composable(NoteGraph.MAIN_SCREEN) {
                Main(navController, preferences)
            }

            composable(NoteGraph.AUTH_SCREEN) {
                AuthScreen(navController, bottomBarVisibility)
            }

            composable(NoteGraph.ADMIN_SCREEN) {
                AdminScreen(navController, bottomBarVisibility)
            }

            composable(NoteGraph.AOTD_SCREEN) {
                AOTD(navController)
            }

            composable(NoteGraph.GENERATOR_SCREEN) {
                RhymeScreen(navController, bottomBarVisibility)
            }

            composable(NoteGraph.PROFILE_SCREEN) {
                ProfileScreen(navController)
            }

            composable(NoteGraph.LIBRARY_SCREEN) {
                Library(navController, preferences, bottomBarVisibility)
            }
            composable(NoteGraph.SETTINGS_SCREEN) {
                Settings(navController, preferences)
            }
            composable(NoteGraph.NOTES_SCREEN) {
                Notes(navController)
            }
            composable(NoteGraph.ARTICLE_SCREEN) {
                ArticleScreen(navController)
            }
            composable(NoteGraph.NOTE_SCREEN) {
                Note(
                    navController,
                    { navController.navigate(NoteGraph.NOTES_SCREEN) },
                    bottomBarVisibility
                )
            }
        }
    }
}