package com.practicecoding.dentalai.data.model

import com.google.gson.annotations.SerializedName

data class Prediction(
    @SerializedName("x") val x: Float,
    @SerializedName("y") val y: Float,
    @SerializedName("width") val width: Float,
    @SerializedName("height") val height: Float,
    @SerializedName("confidence") val confidence: Float,
    @SerializedName("class") val className: String, // Variable to store class name
    @SerializedName("class_id") val classId: Int,
    @SerializedName("detection_id") val detectionId: String)

data class InferenceResponse(
    val predictions: List<Prediction>
)