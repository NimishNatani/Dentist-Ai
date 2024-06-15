package com.practicecoding.dentalai.general

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.window.Dialog
import com.practicecoding.dentalai.ui.theme.Crimson

@Composable
fun CommonLoading(){
    Dialog(onDismissRequest = {  }) {
        CircularProgressIndicator(modifier = Modifier.onGloballyPositioned {  }, color = Crimson)
        
    }
}