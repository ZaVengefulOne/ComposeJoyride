package com.example.composejoyride.ui.viewModels

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.example.composejoyride.data.utils.SEARCH_KEY

class LibraryViewModel(application: Application) : ViewModel() {

    fun saveSearchHistory(query: String, sharedPreferences: SharedPreferences) {
        val historySet =
            sharedPreferences.getStringSet(SEARCH_KEY, mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()
        if (historySet.contains(query)) {
            historySet.remove(query)
        }
        historySet.add(query)
        if (historySet.size > 10) {
            val iterator = historySet.iterator()
            for (i in 1..historySet.size - 10) {
                iterator.next()
                iterator.remove()
            }
        }
        sharedPreferences.edit {
            putStringSet(SEARCH_KEY, historySet)
        }
    }
}