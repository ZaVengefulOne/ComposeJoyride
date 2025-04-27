package com.example.composejoyride.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.composejoyride.data.repositories.TopicsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(repository: TopicsRepository) : ViewModel() {
}