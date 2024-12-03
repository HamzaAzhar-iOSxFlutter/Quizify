package com.example.quizify.repository

import android.util.Log
import com.example.quizify.data.DataOrException
import com.example.quizify.model.QuizModelItem
import com.example.quizify.network.QuizApi
import javax.inject.Inject

class QuizRepository @Inject constructor(private val api: QuizApi) {

    /*
    ArrayList: Mutable implementation of List (can add/remove items). Use it when you need a dynamic list.
     */
    private val dataOrException = DataOrException<ArrayList<QuizModelItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuizModelItem>, Boolean, java.lang.Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()

            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.loading = false
            }

        } catch(exception: Exception) {
            dataOrException.exception = exception
            Log.d("Exception", "getAllQuestions: ${dataOrException.exception!!.localizedMessage}")
        }
        return dataOrException
    }
}