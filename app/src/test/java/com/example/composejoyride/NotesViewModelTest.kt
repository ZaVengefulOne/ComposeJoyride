package com.example.composejoyride


import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.repositories.NotesRepository
import com.example.composejoyride.ui.viewModels.NotesViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {

    private lateinit var repository: NotesRepository
    private lateinit var viewModel: NotesViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testNotes = listOf(
        Note(1, "Note 1", "Text 1"),
        Note(2, "Note 2", "Text 2")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        every { repository.allNotes } returns MutableStateFlow(testNotes)
        viewModel = NotesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `allNotes should expose notes from repository`() = runTest {
        val results = mutableListOf<List<Note>>()
        val job = launch {
            viewModel.allNotes.collect {
                results.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()
        assertEquals(testNotes, results.last())
    }

    @Test
    fun `clearNotes should call repository deleteAll`() = runTest {
        coEvery { repository.deleteAll() } just Runs

        viewModel.clearNotes()
        advanceUntilIdle()

        coVerify { repository.deleteAll() }
    }
}
