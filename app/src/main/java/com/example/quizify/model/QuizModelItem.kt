package com.example.quizify.model

data class QuizModelItem(
    val answer: String,
    val category: String,
    val choices: List<String>,
    val question: String
)