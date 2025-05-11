package com.example.composejoyride.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.repositories.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NotesRepository): ViewModel() {

    private val _note = MutableStateFlow(Note(35,"Test", "Test"))
    val note: StateFlow<Note> get() = _note

    private suspend fun findNote(name: String) {
        _note.value = repository.findNote(name) ?: Note(0, note_content_html = "Ошибка!")
    }

    fun setNote(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            findNote(name)
        }
    }

    fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {
            _note.value.note_name?.let { repository.deleteNote(it) }
        }
    }


    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }

    fun updateNoteName(newName: String) {
        _note.value = _note.value.copy(note_name = newName)
    }

    fun updateNoteText(newText: String) {
        _note.value = _note.value.copy(note_content_html = newText)
    }

    fun updateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            _note.value.note_name?.let {
                repository.updateNote(_note.value.id,
                    it, _note.value.note_content_html)
            }
        }
    }
}