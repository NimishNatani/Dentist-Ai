package com.practicecoding.dentalai.screen.category.reportscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.practicecoding.dentalai.viewmodel.GetUserDataViewModel
import com.practicecoding.dentalai.viewmodel.MainEvent

@Composable
fun TeethImageScreen(viewModel: GetUserDataViewModel = hiltViewModel()){

    LaunchedEffect(Unit) {
        viewModel.onEvent(MainEvent.GetUser)

    }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Image(  painter = rememberAsyncImagePainter(
            model =viewModel.user.value.simileteeth
        ) , contentDescription ="simile teeth" , modifier = Modifier.size(200.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Image(  painter = rememberAsyncImagePainter(
            model =viewModel.user.value.lowerteeth
        ) , contentDescription ="simile teeth", modifier = Modifier.size(200.dp) )
        Spacer(modifier = Modifier.height(10.dp))
        Image(  painter = rememberAsyncImagePainter(
            model =viewModel.user.value.upperteeth
        ) , contentDescription ="simile teeth" , modifier = Modifier.size(200.dp))
        Spacer(modifier = Modifier.height(10.dp))
    }
}