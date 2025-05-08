package com.example.composejoyride.ui.viewModels

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.repositories.RhymeRepository
import com.example.composejoyride.di.models.Rhyme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class RhymeViewModel @Inject constructor(private val repository: RhymeRepository): ViewModel() {

    private val _input = MutableStateFlow("")
    val input: StateFlow<String> get() = _input

    private val _result = MutableStateFlow<List<String>>(listOf())
    val result: StateFlow<List<String>> get() = _result


    fun setInput(newInput: String){
        _input.value = newInput
    }

    fun findRhymes(stress: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _result.value = repository.getRhymes(Rhyme(input.value, stress))
            } catch (e: Exception) {
                _result.value = listOf("Ошибка!")
            }
        }
    }


    fun copyToClipBoard(context: Context, rhymeItem: String) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("text", rhymeItem)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Скопировано в буфер обмена!", Toast.LENGTH_LONG).show()
    }
}