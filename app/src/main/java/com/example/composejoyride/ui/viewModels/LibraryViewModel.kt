package com.example.composejoyride.ui.viewModels

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.toArticles
import com.example.composejoyride.data.utils.toCacheArticles
import com.example.composejoyride.di.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(private val repository: ArticlesRepository) :
    ViewModel() {

    private val _articleItems = MutableStateFlow<List<Article>>(emptyList())
    val articleItems: StateFlow<List<Article>> get() = _articleItems

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> get() = _isLoaded


    fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //repository.clearCache()
                val articles = repository.getArticles()
                _articleItems.value = articles
                if (articles.isNotEmpty()) {
                    cacheArticles(articles)
                }
                _isLoaded.value = true
            } catch (e: Exception) {
                _articleItems.value = getCache()
                _isLoaded.value = true
            }
        }
    }

    private fun cacheArticles(articlesList: List<Article>) {
        viewModelScope.launch(Dispatchers.IO) {
            val fullArticles: ArrayList<Article> = arrayListOf()
            articlesList.forEach {
                val curArticle = repository.getArticle(it.articleLink)
                fullArticles.add(curArticle)
            }
            fullArticles.toList()
            val finalArticles = fullArticles.toCacheArticles()
            repository.cacheArticles(finalArticles)
        }
    }

    private suspend fun getCache(): List<Article> {
        val articles = repository.getSavedArticles()
        return articles.toArticles()
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