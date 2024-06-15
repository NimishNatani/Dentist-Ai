package com.practicecoding.dentalai.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicecoding.dentalai.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicecoding.dentalai.general.GeneralButton
import com.practicecoding.dentalai.ui.theme.Crimson
import com.practicecoding.dentalai.ui.theme.EggBlue
import com.practicecoding.dentalai.viewmodel.SensorViewModel

@Composable
fun HorizontalScreen() {
    val viewModel: SensorViewModel = viewModel()
    val isTilted by viewModel.isTilted

    if (isTilted) {
        TiltedUIScreen()
    } else {
//        TiltAnimation(image = painterResource(id = R.drawable.screenrotate))
    }
}

@Composable
fun TiltedUIScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.theetscanimage),
            contentDescription = "Upper teeth",
            modifier = Modifier
//                .weight(1f)
                .height(380.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(EggBlue)
                .rotate(90f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

                Text(text = "Smile Wide !", fontSize = 16.sp, fontWeight = FontWeight.SemiBold,color = Color.White)
                Text(text = "Open your mouth comfortably and show us all your teeth in a big smile.", fontSize = 12.sp, color = Color.White)

            Spacer(modifier = Modifier.height(8.dp))


                Text(text = "Capture Your Top Teeth:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold,color = Color.White)
                Text(text = " Tilt your phone slightly upwards and take a selfie focusing on your upper teeth (maxillary arch).", fontSize = 12.sp, color = Color.White)

            Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Get Those Bottom Pearls:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold,color = Color.White)
                Text(text = " Tilt your phone slightly downwards and snap another selfie focusing on your lower teeth (mandibular arch).", fontSize = 12.sp, color = Color.White)

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { /* Your action */ }, colors = ButtonColors(Crimson, Crimson, Crimson,
                Crimson) ){
                Text("Let's scan",color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun TiltAnimation(image: Painter) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Image(
            painter = image,
            contentDescription = "Tilt Phone",
            modifier = Modifier.rotate(angle)
        )
    }
}