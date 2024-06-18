package com.practicecoding.dentalai.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicecoding.dentalai.data.UserRepository
import com.practicecoding.dentalai.data.model.InferenceResponse
import com.practicecoding.dentalai.data.model.Prediction
import com.practicecoding.dentalai.data.model.UserModel
import com.practicecoding.dentalai.general.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class GetUserDataViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {
    private val _user = mutableStateOf(UserModel())
    val user: State<UserModel> = _user

    var isDialog = mutableStateOf(false)

    suspend fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetUser -> getUser()
            is MainEvent.CaptureImage -> captureImage(
                event.imageCapture,
                event.context,
                event.idx,
                event.onSuccess,
                event.onFailure
            )

            else -> {}
        }
    }

    private suspend fun getUser() {
        viewModelScope.launch {
            _user.value = repo.getUser()
        }
    }

    private suspend fun captureImage(
        imageCapture: ImageCapture,
        context: Context,
        idx: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        // Create a temporary file to store the image
        isDialog.value = true
        val tempFile = withContext(Dispatchers.IO) {
            File.createTempFile("CameraXImage_", ".jpg", context.cacheDir)
        }
        val outputOptions = ImageCapture.OutputFileOptions.Builder(tempFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(tempFile)
                    CoroutineScope(Dispatchers.IO).launch {
                        onPhotoCaptured(savedUri, context, idx, onSuccess, onFailure)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d("ImageCapture", "Failed to capture image", exception)
                    onFailure("Failed to process captured image: ${exception.message}")

                }
            }
        )
    }

    private suspend fun onPhotoCaptured(
        uri: Uri,
        context: Context,
        idx: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val bitmap = getBitmapFromUri(uri, context)
            if (bitmap != null) {
                val tempFile = File(context.cacheDir, "temp_image.jpg")
                val outputStream = withContext(Dispatchers.IO) {
                    FileOutputStream(tempFile)
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                withContext(Dispatchers.IO) {
                    outputStream.close()
                }

                val requestBody = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", tempFile.name, requestBody)

                val call = RetrofitClient.inferenceRepository.infer("gp-dental/2", filePart)

                call.enqueue(object : Callback<InferenceResponse> {
                    override fun onResponse(
                        call: Call<InferenceResponse>,
                        response: Response<InferenceResponse>
                    ) {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (response.isSuccessful) {
                                val inferenceResponse = response.body()
                                if (inferenceResponse != null) {
                                    // Draw bounding boxes on the image
                                    val resultBitmap =
                                        drawBoundingBoxes(bitmap, inferenceResponse.predictions)

                                    val resultFile = File(
                                        context.cacheDir,
                                        "result_image${idx}.jpg"
                                    )
                                    val resultOutputStream = FileOutputStream(resultFile)
                                    resultBitmap.compress(
                                        Bitmap.CompressFormat.JPEG,
                                        100,
                                        resultOutputStream
                                    )
                                    resultOutputStream.close()
                                    val resultUri = Uri.fromFile(resultFile)
                                    repo.uploadImage(
                                        resultUri, context,
                                        onSuccess = {
                                            isDialog.value = false
                                            Toast.makeText(
                                                context,
                                                "Image Scan Completely",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            onSuccess()
                                        },
                                        onFailure = { exception ->
                                            isDialog.value = false
                                            Log.d(
                                                "Image upload failed",
                                                exception.message.toString()
                                            )
                                            onFailure("Image upload failed: ${exception.message}")

                                        }, idx
                                    )
                                }
                            } else {
                                isDialog.value = false
                                Log.d("Inference failed", response.message())
                                onFailure("Inference failed: ${response.message()}")

                            }
                        }
                    }

                    override fun onFailure(call: Call<InferenceResponse>, t: Throwable) {
                        isDialog.value = false
                        Log.d("Inference request failed", t.message.toString())
                        onFailure("${t.message}")
                    }
                })
            } else {
                isDialog.value = false
                Log.d("Failed to get Bitmap from URI", uri.toString())
                onFailure("Failed")
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri, context: Context): Bitmap? {
        return context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        }
    }

    private fun drawBoundingBoxes(
        bitmap: Bitmap,
        predictions: List<Prediction>,
        confidenceThreshold: Float = 0.70f
    ): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 2f
            textSize = 16f
        }

        for (prediction in predictions.filter { it.confidence > confidenceThreshold }) {
            val left = prediction.x - prediction.width / 2
            val top = prediction.y - prediction.height / 2
            val right = prediction.x + prediction.width / 2
            val bottom = prediction.y + prediction.height / 2
            canvas.drawRect(left, top, right, bottom, paint)

            // Draw text with class name and confidence level
            val text = "${prediction.className}: ${String.format("%.2f", prediction.confidence)}"
            canvas.drawText(text, left, top - 10, paint)
        }

        return mutableBitmap
    }
}

sealed class MainEvent {
    data object GetUser : MainEvent()
    data class CaptureImage(
        val imageCapture: ImageCapture,
        val context: Context,
        val idx: Int,
        val onSuccess: () -> Unit,
        val onFailure: (String) -> Unit
    ) : MainEvent()
}
