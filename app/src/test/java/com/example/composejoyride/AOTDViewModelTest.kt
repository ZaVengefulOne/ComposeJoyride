package com.example.composejoyride

import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.ui.viewModels.AOTDViewModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.example.composejoyride.di.models.Article

@OptIn(ExperimentalCoroutinesApi::class)
class AOTDViewModelTest {

    private lateinit var repository: ArticlesRepository
    private lateinit var viewModel: AOTDViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = AOTDViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRandomArticle should load article data correctly`() = runTest {
        val article = Article("Заголовок", "Текст", "https://link")
        coEvery { repository.getRandomArticle() } returns article

        viewModel.getRandomArticle()
        advanceUntilIdle()

        val name = viewModel.randomArticleName.first { it.isNotEmpty() }
        val text = viewModel.randomArticleText.first { it.isNotEmpty() }
        val link = viewModel.randomArticleLink.first { it.isNotEmpty() }

        assertEquals("Заголовок", name)
        assertEquals("Текст", text)
        assertEquals("https://link", link)
        assertTrue(viewModel.isLoaded.first())
        assertEquals(false, viewModel.showPB.first())
    }

//    @Test
//    fun `getRandomArticle should handle exception`() = runTest {
//        coEvery { repository.getRandomArticle() } throws Exception("Ошибка!")
//
//        viewModel.getRandomArticle()
//        advanceUntilIdle()
//
//        assertEquals("Ошибка! Статья не найдена!", viewModel.randomArticleName.first())
//        assertTrue(viewModel.isLoaded.first())
//        assertEquals(false, viewModel.showPB.first())
//    }
}
