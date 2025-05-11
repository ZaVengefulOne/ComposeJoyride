package com.example.composejoyride.ui.viewModels

import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.ui.theme.TheFont
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(repository: ArticlesRepository): ViewModel() {

    fun setFont(font: FontFamily){
        TheFont = font
    }
}