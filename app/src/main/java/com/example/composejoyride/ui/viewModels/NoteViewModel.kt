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

    val _note = MutableStateFlow(Note())
    val note: StateFlow<Note> get() = _note

    private suspend fun findNote(id: Int) {
        _note.value = repository.findNote(id) ?: Note()
    }

    fun setNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            findNote(id)
        }
    }

    fun createAndOpenNewNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = repository.insertNote(Note())
            findNote(id.toInt())
        }
    }

    fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(_note.value.id)
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
                repository.updateNote(_note.value.id,
                    _note.value.note_name, _note.value.note_content_html)
            }
        }
}