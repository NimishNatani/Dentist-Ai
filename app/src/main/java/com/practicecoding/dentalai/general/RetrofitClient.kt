package com.practicecoding.dentalai.general

import com.practicecoding.dentalai.ApiKeyInterceptor
import com.practicecoding.dentalai.BuildConfig
import com.practicecoding.dentalai.data.InferenceRepository
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://detect.roboflow.com/" // Replace with your actual base URL
    val apikey = BuildConfig.dentalKey
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(apikey))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val inferenceRepository: InferenceRepository = retrofit.create(InferenceRepository::class.java)
}
