package com.example.composejoyride.ui.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import com.example.composejoyride.R
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.ui.theme.ttFamily
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(repository: ArticlesRepository): ViewModel() {

    fun setFont(font: FontFamily){
        ttFamily = font
    }
}