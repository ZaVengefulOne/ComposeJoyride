package com.example.composejoyride.ui.viewModels

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.data.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(private val repository: ArticlesRepository) : ViewModel() {

//    private val _chosenArticleURL = MutableStateFlow("")
//    val chosenArticleURL: StateFlow<String> get() = _chosenArticleURL

    private val _articleItems = MutableStateFlow(listOf(listOf("Тест", "Тест")))
    val articleItems: StateFlow<List<List<String>>> get() = _articleItems

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> get() = _isLoaded



    fun getArticles(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _articleItems.value = repository.getArticles()
                _isLoaded.value = true
            } catch (e: Exception) {
                _articleItems.value = listOf(listOf("Ошибка!", "Ошибка!"))
                _isLoaded.value = true
            }
        }
    }

    fun saveSearchHistory(query: String, sharedPreferences: SharedPreferences) {
        val historySet =
            sharedPreferences.getStringSet(Constants.SEARCH_KEY, mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()
        if (historySet.contains(query)) {
            historySet.remove(query)
        }
        historySet.add(query)
        if (historySet.size > 10) {
            val iterator = historySet.iterator()
            for (i in 1..historySet.size - 10) {
                iterator.next()
                iterator.remove()
            }
        }
        sharedPreferences.edit {
            putStringSet(Constants.SEARCH_KEY, historySet)
        }
    }
}