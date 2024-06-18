package com.practicecoding.dentalai

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val urlWithApiKey = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey) // Adjust this as per your API's requirements
            .build()
        val requestWithApiKey = originalRequest.newBuilder().url(urlWithApiKey).build()
        return chain.proceed(requestWithApiKey)
    }
}