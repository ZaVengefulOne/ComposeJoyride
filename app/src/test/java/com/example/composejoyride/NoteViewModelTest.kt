package com.example.composejoyride


import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.repositories.NotesRepository
import com.example.composejoyride.ui.viewModels.NoteViewModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {

    private lateinit var repository: NotesRepository
    private lateinit var viewModel: NoteViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testNote = Note(id = 1, note_name = "Test", note_content_html = "Text")

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = NoteViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setNote loads note from repository`() = runTest {
        coEvery { repository.findNote(1) } returns testNote

        viewModel.setNote(1)
        advanceUntilIdle()

        val result = viewModel.note.first()
        assertEquals(testNote, result)
    }

    @Test
    fun `createAndOpenNewNote inserts and loads new note`() = runTest {
        val insertedNote = testNote.copy(id = 2)
        coEvery { repository.insertNote(any()) } returns 2L
        coEvery { repository.findNote(2) } returns insertedNote

        viewModel.createAndOpenNewNote()
        advanceUntilIdle()
        coVerify { repository.findNote(2) }
        val result = viewModel.note.value
        assertEquals(2, result.id)
        assertEquals(insertedNote, result)
    }

    @Test
    fun `deleteNote calls repository with correct id`() = runTest {
        viewModel.updateNoteName("ToDelete")
        viewModel.updateNoteText("Sample text")
        viewModel._note.value = testNote

        coEvery { repository.deleteNote(1) } just Runs

        viewModel.deleteNote()
        advanceUntilIdle()

        coVerify { repository.deleteNote(1) }
    }

    @Test
    fun `insertNote calls repository insert`() = runTest {
        val newNote = Note(id = 5, note_name = "Insert", note_content_html = "Here")

        coEvery { repository.insertNote(newNote) } just Awaits

        viewModel.insertNote(newNote)
        advanceUntilIdle()

        coVerify { repository.insertNote(newNote) }
    }

    @Test
    fun `updateNoteName updates note state`() {
        viewModel.updateNoteName("Новое имя")
        assertEquals("Новое имя", viewModel.note.value.note_name)
    }

    @Test
    fun `updateNoteText updates note state`() {
        viewModel.updateNoteText("Содержимое")
        assertEquals("Содержимое", viewModel.note.value.note_content_html)
    }

    @Test
    fun `updateNote sends data to repository`() = runTest {
        val updatedNote = Note(id = 10, note_name = "Updated", note_content_html = "HTML")

        viewModel._note.value = updatedNote

        coEvery { repository.updateNote(10, "Updated", "HTML") } just Runs

        viewModel.updateNote()
        advanceUntilIdle()

        coVerify { repository.updateNote(10, "Updated", "HTML") }
    }
}
