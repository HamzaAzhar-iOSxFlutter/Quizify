package com.example.quizify.network

import com.example.quizify.model.QuizModel
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuizApi {
    @GET("world.json")
    suspend fun getAllQuestions(): QuizModel
}