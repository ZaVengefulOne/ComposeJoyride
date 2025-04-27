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
class ArticleViewModel @Inject constructor(private val repository: ArticlesRepository) : ViewModel() {

    private val _arcticleName = MutableStateFlow("Статья загружается, пожалуйста, подождите...")
    val arcticleName: StateFlow<String> get() = _arcticleName

    private val _arcticleText = MutableStateFlow("")
    val arcticleText: StateFlow<String> get() = _arcticleText

    fun getArticle(url: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val article = repository.getArticle(url)
                _arcticleName.value = article[0]
                _arcticleText.value = article[1]
            } catch (e: Exception){
             _arcticleName.value = "Ошибка!"
            }
        }
    }

    fun articleDrop(){
        _arcticleName.value = "Статья загружается, пожалуйста, подождите..."
        _arcticleText.value = ""
    }

}