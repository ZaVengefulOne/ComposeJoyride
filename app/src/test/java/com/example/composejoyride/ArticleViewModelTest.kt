package com.example.composejoyride


import com.example.composejoyride.data.entitites.CacheArticle
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.ui.viewModels.ArticleViewModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.example.composejoyride.di.models.Article
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleViewModelTest {

    private lateinit var repository: ArticlesRepository
    private lateinit var viewModel: ArticleViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ArticleViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getArticle loads article from repository`() = runTest {
        val article = Article("Заголовок", "Контент", "url")
        coEvery { repository.getArticle("url") } returns article

        viewModel.getArticle("url")
        advanceUntilIdle()

        val name = viewModel.articleName.first { it == "Заголовок" }
        val text = viewModel.articleText.first { it == "Контент" }

        assertEquals("Заголовок", name)
        assertEquals("Контент", text)
    }

    @Test
    fun `getArticle falls back to saved article on failure`() = runTest {
        coEvery { repository.getArticle("url") } throws Exception("Ошибка сети")
        val savedArticle = CacheArticle(0, "Локальная статья", "Локальный текст", "url")
        coEvery { repository.getSavedArticle("url") } returns savedArticle

        viewModel.getArticle("url")
        advanceUntilIdle()

        val name = viewModel.articleName.first { it == "Локальная статья" }
        val text = viewModel.articleText.first { it == "Локальный текст" }

        assertEquals("Локальная статья", name)
        assertEquals("Локальный текст", text)
    }

    @Test
    fun `articleDrop resets article state`() = runTest {
        viewModel.articleDrop()

        val name = viewModel.articleName.first()
        val text = viewModel.articleText.first()

        assertEquals("Статья загружается, пожалуйста, подождите...", name)
        assertEquals("", text)
    }
}
