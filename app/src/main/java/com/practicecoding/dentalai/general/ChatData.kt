package com.practicecoding.dentalai.general

import com.google.ai.client.generativeai.GenerativeModel
import com.practicecoding.dentalai.data.model.Chat
import kotlinx.coroutines.Dispatchers
import com.google.ai.client.generativeai.type.generationConfig
import com.practicecoding.dentalai.BuildConfig
import kotlinx.coroutines.withContext

object ChatData {
    val api_key = BuildConfig.apiKey
    private val config = generationConfig {
        temperature = 0.7f
    }

    suspend fun getResponse(prompt: String): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash-latest", apiKey = api_key,
            generationConfig = config

        )

        try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }

            return Chat(
                prompt = response.text ?: "error",
                isFromUser = false
            )

        } catch (e: Exception) {
            return Chat(
                prompt = e.message ?: "error",
                isFromUser = false
            )
        }

    }
}