package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.model.assistant.AssistantAnswer
import com.lukabaia.yummy.model.assistant.SuggestsInfo
import com.lukabaia.yummy.repository.AssistantRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AssistantViewModel(
    private val repository: AssistantRepository
): ViewModel() {

    private val _suggestsFlow = MutableSharedFlow<SuggestsInfo>()
    val suggestsFlow get() = _suggestsFlow.asSharedFlow()

    private val _answerFlow = MutableSharedFlow<AssistantAnswer>()
    val answerFlow get() = _answerFlow.asSharedFlow()

    suspend fun getSuggests(query: String) {
        repository.getSuggests(query)?.let {
            _suggestsFlow.emit(it)
        }
    }

    suspend fun getAssistantAnswer(text: String) {
        repository.getAssistantAnswer(text)?.let {
            _answerFlow.emit(it)
        }
    }

}