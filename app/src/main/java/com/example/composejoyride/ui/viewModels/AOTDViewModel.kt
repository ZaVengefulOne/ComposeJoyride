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

    private val _randomArcticleName = MutableStateFlow("")
    val randomArcticleName: StateFlow<String> get() = _randomArcticleName

    private val _randomArcticleText = MutableStateFlow("")
    val randomArcticleText: StateFlow<String> get() = _randomArcticleText

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> get() = _isLoaded

    private val _showPB = MutableStateFlow(false)
    val showPB: StateFlow<Boolean> get() = _showPB

    fun getRandomArticle(){
        _showPB.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val randomArcticle = repository.getRandomArticle()
                _randomArcticleName.value = randomArcticle[0]
                _randomArcticleText.value = randomArcticle[1]
            } catch (e: Exception)
            {
                _randomArcticleName.value = "Ошибка! Статья не найдена!"
            }
            _isLoaded.value = true
            _showPB.value = false
        }
    }

}