package com.example.composejoyride.ui.viewModels

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.repositories.RhymeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class RhymeViewModel @Inject constructor(repository: RhymeRepository): ViewModel() {

    private val _input = MutableStateFlow("")
    val input: StateFlow<String> get() = _input

    private val _result = MutableStateFlow<List<String>>(listOf())
    val result: StateFlow<List<String>> get() = _result


    fun setInput(newInput: String){
        _input.value = newInput
    }

    fun findRhymes() {
        val gfgThread = Thread {
            try {
                val document =
                    Jsoup.connect("https://rifme.net/r/${input.value}/0")
                        .get()
                val rhyme = document.getElementsByClass("riLi")
                _result.value = rhyme.map { it.text().toString() }
            } catch (e: Exception) {
                _result.value = listOf("Ошибка!")
            }
        }
        gfgThread.start()
    }

    fun copyToClipBoard(context: Context, rhymeItem: String) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("text", rhymeItem)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Скопировано в буфер обмена!", Toast.LENGTH_LONG).show()
    }
}