package com.example.composejoyride.data.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.composejoyride.R


val CustomFontFamily = FontFamily(Font(R.font.tippytoesbold))

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Главная",
            icon = Icons.Filled.Home,
            route = NoteGraph.MAIN_SCREEN
        ),
        BottomNavItem(
            label = "Статья",
            icon = Icons.Filled.ContactPage,
            route = NoteGraph.AOTD_SCREEN
        ),
        BottomNavItem(
            label = "Генератор",
            icon = Icons.Filled.Abc,
            route = NoteGraph.GENERATOR_SCREEN
        ),
        BottomNavItem(
            label = "Библиотека",
            icon = Icons.Filled.AutoStories,
            route = NoteGraph.LIBRARY_SCREEN
        )

    )

    const val PREFERENCES = "vengeful_preferences"
    const val EDIT_KEY = "dark_theme"
    const val SEARCH_KEY = "search_key"
    const val BASE_RHYMES_URL = "https://rifme.net/r/"
    val vowels = setOf('а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я',
        'А', 'Е', 'Ё', 'И', 'О', 'У', 'Ы', 'Э', 'Ю', 'Я')
}