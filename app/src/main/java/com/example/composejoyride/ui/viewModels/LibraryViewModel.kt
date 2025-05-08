package com.example.composejoyride.ui.viewModels

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
class LibraryViewModel @Inject constructor(private val repository: ArticlesRepository) : ViewModel() {

//    private val _chosenArticleURL = MutableStateFlow("")
//    val chosenArticleURL: StateFlow<String> get() = _chosenArticleURL

    private val _articleItems = MutableStateFlow(listOf(Article("", null, "")))
    val articleItems: StateFlow<List<Article>> get() = _articleItems

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> get() = _isLoaded



    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun getArticles(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //repository.clearCache()
                val articles = repository.getArticles()
                _articleItems.value = articles
                if (articles.isNotEmpty()) {
                    Log.d("OBTAINED_ARTICLES", articles.toString())
                    cacheArticles(articles)
                }
                _isLoaded.value = true
            } catch (e: Exception) {
                Log.d("VENGEFUL_ERROR", e.toString())
                _articleItems.value = getCache()
                _isLoaded.value = true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun cacheArticles(articlesList: List<Article>){
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

    private suspend fun getCache(): List<Article>{
        val articles = repository.getSavedArticles()
        Log.d("SAVED_ARTICLES", articles.toString())
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