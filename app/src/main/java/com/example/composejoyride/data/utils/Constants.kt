package com.example.composejoyride.data.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.composejoyride.R
import com.example.composejoyride.data.models.BottomNavItem

const val PREFERENCES = "vengeful_preferences"
const val EDIT_KEY = "dark_theme"
val CustomFontFamily = FontFamily(Font(R.font.tippytoesbold))

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Главная",
            icon = Icons.Filled.Home,
            route = "main"
        ),
        BottomNavItem(
            label = "Статья дня",
            icon = Icons.AutoMirrored.Filled.List,
            route = "aotd"
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