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
class AOTDViewModel @Inject constructor(private val repository: ArticlesRepository) : ViewModel() {

    private val _randomArticleName = MutableStateFlow("")
    val randomArticleName: StateFlow<String> get() = _randomArticleName

    private val _randomArticleText = MutableStateFlow("")
    val randomArticleText: StateFlow<String> get() = _randomArticleText

    private val _randomArticleLink = MutableStateFlow("")
    val randomArticleLink: StateFlow<String> get() = _randomArticleLink

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> get() = _isLoaded

    private val _showPB = MutableStateFlow(false)
    val showPB: StateFlow<Boolean> get() = _showPB

    fun getRandomArticle() {
        _showPB.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val randomArticle = repository.getRandomArticle()
                _randomArticleName.value = randomArticle.articleTitle
                _randomArticleText.value = randomArticle.articleText ?: ""
                _randomArticleLink.value = randomArticle.articleLink
            } catch (e: Exception) {
                _randomArticleName.value = "Ошибка! Статья не найдена!"
            }
            _isLoaded.value = true
            _showPB.value = false
        }
    }

}