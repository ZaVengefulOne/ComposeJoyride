package com.example.composejoyride.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.repositories.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: ArticlesRepository) :
    ViewModel() {

    private val _articleName = MutableStateFlow("Статья загружается, пожалуйста, подождите...")
    val articleName: StateFlow<String> get() = _articleName

    private val _articleText = MutableStateFlow("")
    val articleText: StateFlow<String> get() = _articleText

    fun getArticle(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val article = repository.getArticle(url)
                _articleName.value = article.articleTitle
                _articleText.value = article.articleText ?: ""
            } catch (e: Exception) {
                val article = repository.getSavedArticle(url)
                _articleName.value = article.articleTitle
                _articleText.value = article.articleText
            }
        }
    }

    fun articleDrop() {
        _articleName.value = "Статья загружается, пожалуйста, подождите..."
        _articleText.value = ""
    }

}