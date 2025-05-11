package com.example.composejoyride

import android.content.SharedPreferences
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.ui.viewModels.LibraryViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class LibraryViewModelTest {

    private lateinit var repository: ArticlesRepository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: LibraryViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        sharedPreferences = mockk(relaxed = true)
        viewModel = LibraryViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `saveSearchHistory stores trimmed query set`() {
        val editor = mockk<SharedPreferences.Editor>(relaxed = true)
        val prefsMap = mutableSetOf("abc", "def", "ghi")
        val slot = slot<Set<String>>()

        every { sharedPreferences.getStringSet(any(), any()) } returns prefsMap
        every { sharedPreferences.edit() } returns editor
        every { editor.putStringSet(any(), capture(slot)) } returns editor

        viewModel.saveSearchHistory("def", sharedPreferences)

        val updated = slot.captured
        assertTrue(updated.contains("def"))
        assertEquals(3, updated.size)
    }

    @Test
    fun `saveSearchHistory limits size to 10`() {
        val editor = mockk<SharedPreferences.Editor>(relaxed = true)
        val largeSet = (1..12).map { "query$it" }.toMutableSet()
        val slot = slot<Set<String>>()

        every { sharedPreferences.getStringSet(any(), any()) } returns largeSet
        every { sharedPreferences.edit() } returns editor
        every { editor.putStringSet(any(), capture(slot)) } returns editor

        viewModel.saveSearchHistory("newQuery", sharedPreferences)

        val updated = slot.captured
        assertTrue(updated.contains("newQuery"))
        assertTrue(updated.size <= 10)
    }
}
