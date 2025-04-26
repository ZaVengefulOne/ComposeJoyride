package com.example.composejoyride.data.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified VM : ViewModel> sharedViewModel(
    navController: NavController,
): VM {
    val parentEntry = remember { navController.getBackStackEntry(NoteGraph.MAIN_SCREEN) }
    return hiltViewModel(parentEntry)
}