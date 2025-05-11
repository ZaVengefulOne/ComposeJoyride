package com.example.composejoyride

import com.example.composejoyride.data.repositories.RhymeRepository
import com.example.composejoyride.di.models.Rhyme
import com.example.composejoyride.ui.viewModels.RhymeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RhymeViewModelTest {

    private lateinit var repository: RhymeRepository
    private lateinit var viewModel: RhymeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = RhymeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setInput updates input value`() {
        viewModel.setInput("берёза")
        assertEquals("берёза", viewModel.input.value)
    }

    @Test
    fun `findRhymes updates result on success`() = runTest {
        viewModel.setInput("дорога")
        val rhymes = listOf("подстрога", "берлога")
        coEvery { repository.getRhymes(Rhyme("дорога", 2)) } returns rhymes

        viewModel.findRhymes(2)
        advanceUntilIdle()

        runBlocking {
            delay(100)
            assertEquals(rhymes, viewModel.result.value)
        }
    }

    @Test
    fun `findRhymes sets Error on failure`() = runTest {
        viewModel.setInput("море")
        coEvery { repository.getRhymes(any()) } throws Exception("fail")

        viewModel.findRhymes(1)
        advanceUntilIdle()
        runBlocking {
            delay(100)
            assertEquals(listOf("Ошибка!"), viewModel.result.value)
        }
    }
}
