package com.practicecoding.dentalai.screen.category.reportscreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.Screens
import com.practicecoding.dentalai.ui.theme.darkBlue
import com.practicecoding.dentalai.viewmodel.GetUserDataViewModel
import com.practicecoding.dentalai.viewmodel.MainEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraPreviewScreen(
    navHostController: NavHostController,
    viewModel: GetUserDataViewModel = hiltViewModel()
) {
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    val currentImageResource = remember { mutableIntStateOf(R.drawable.girlimage1) }

    if (viewModel.isDialog.value) {
        Dialog(
            onDismissRequest = { /*TODO*/ },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            CircularProgressIndicator(color = darkBlue)
        }
    }
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        // Camera preview in the top half
        Box(modifier = Modifier.weight(1f)) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        }
        // Image and camera button in the bottom half
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = currentImageResource.intValue), // Replace with your image resource
                contentDescription = "Your Image",
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.camera), // Replace with your camera icon resource
                contentDescription = "Camera",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(64.dp) // Adjust size as needed
                    .clickable {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.onEvent(MainEvent.CaptureImage(imageCapture, context,currentImageResource.intValue,
                                onSuccess = {
                                    // Update the image resource after successful capture
                                    when (currentImageResource.intValue) {
                                        R.drawable.girlimage1 -> currentImageResource.intValue = R.drawable.girlimage2
                                        R.drawable.girlimage2 -> currentImageResource.intValue = R.drawable.girlimage3
                                        else -> {

                                                navHostController.navigate(Screens.TeethImageScreen.route)

                                        }
                                    }
                                },
                                onFailure = { errorMessage ->
                                    // Handle failure, show error message if needed
                                    Log.d("CaptureImage", "Failed: $errorMessage")
                                    Toast.makeText(context,errorMessage,Toast.LENGTH_LONG).show()
                                }
                            ))
                        }
                    }
            )
        }
    }
}


private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }


