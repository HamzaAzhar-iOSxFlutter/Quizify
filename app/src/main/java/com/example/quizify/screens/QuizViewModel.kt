package com.example.quizify.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizify.data.DataOrException
import com.example.quizify.model.QuizModelItem
import com.example.quizify.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val repository: QuizRepository): ViewModel() {
     val data: MutableState<DataOrException<ArrayList<QuizModelItem>, Boolean, Exception>> = mutableStateOf(
       DataOrException(null, true, Exception("")))

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestions()

            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false

            }
        }
    }

    fun isLoading(): Boolean {
        if (this.data.value.loading == true) {
            return true
        } else {
            return false
        }
    }
}