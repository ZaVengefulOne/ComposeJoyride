package com.example.composejoyride.Utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import com.example.composejoyride.R
import com.example.composejoyride.models.BottomNavItem

const val PREFERENCES = "vengeful_preferences"
const val EDIT_KEY = "dark_theme"
const val SEARCH_SET_KEY = "search_key"

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Главная",
            icon = Icons.Filled.Home,
            route = "main"
        ),
        BottomNavItem(
            label = "Библиотека",
            icon = Icons.Filled.List,
            route = "library"
        ),
        BottomNavItem(
            label = "Генератор рифм",
            icon = Icons.Filled.Star,
            route = "rhyme"
        ),
        BottomNavItem(
            label = "Список статей",
            icon = Icons.Filled.Menu,
            route = "list"
        )

    )
}