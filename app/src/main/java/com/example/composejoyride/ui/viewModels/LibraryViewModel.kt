package com.example.composejoyride.ui.viewModels

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.data.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(repository: ArticlesRepository) : ViewModel() {

    private val _chosenArticleURL = MutableStateFlow("")
    val chosenArticleURL: StateFlow<String> get() = _chosenArticleURL


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