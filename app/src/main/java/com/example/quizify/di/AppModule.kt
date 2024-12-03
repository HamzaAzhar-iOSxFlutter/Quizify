package com.example.quizify.di

import com.example.quizify.network.QuizApi
import com.example.quizify.repository.QuizRepository
import com.example.quizify.utilities.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
@Module: Marks this class as a provider of dependencies.
@InstallIn(SingletonComponent::class): The dependencies provided are available as singletons for the entire app lifecycle.
@Provides: Specifies how to create a dependency.
provideQuizApi(): Creates a QuizApi instance using Retrofit with a base URL and a JSON converter.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideQuizRepository(api: QuizApi): QuizRepository {
        return QuizRepository(api)
    }

    @Singleton
    @Provides
    fun provideQuizApi(): QuizApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApi::class.java)
    }
}