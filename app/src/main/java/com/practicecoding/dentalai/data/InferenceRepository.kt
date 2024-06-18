package com.practicecoding.dentalai.data

import com.practicecoding.dentalai.data.model.InferenceResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface InferenceRepository {
    @Multipart
    @POST("/gp-dental/2") // Replace with your actual endpoint
    fun infer(
        @Query("model_id") modelId: String,
        @Part file: MultipartBody.Part
    ): Call<InferenceResponse>
}
