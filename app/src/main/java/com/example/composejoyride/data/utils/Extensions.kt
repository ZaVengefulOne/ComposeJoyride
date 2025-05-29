package com.example.composejoyride.data.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.composejoyride.data.entitites.CacheArticle
import com.example.composejoyride.di.models.Article
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified VM : ViewModel> sharedViewModel(
    navController: NavController,
): VM {
    val parentEntry = remember { navController.getBackStackEntry(NoteGraph.ROOT) }
    return hiltViewModel(parentEntry)
}

fun List<CacheArticle>.toArticles(): List<Article>{
    return map { cacheItem ->
        Article(
            articleTitle = cacheItem.articleTitle,
            articleText = cacheItem.articleText,
            articleLink = cacheItem.articleLink
        )
    }
}

fun List<Article>.toCacheArticles(): List<CacheArticle> {
    return mapIndexed { index, articleItem ->
        CacheArticle(
            id = index + 1,
            articleTitle = articleItem.articleTitle,
            articleText = articleItem.articleText ?: "",
            articleLink = articleItem.articleLink
        )
    }
}

fun formatTimestamp(timestamp: Any?): String {
    return try {
        val millis = (timestamp as? Long) ?: return "Неизвестно"
        val date = Date(millis)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        format.format(date)
    } catch (e: Exception) {
        "Неизвестно"
    }
}

