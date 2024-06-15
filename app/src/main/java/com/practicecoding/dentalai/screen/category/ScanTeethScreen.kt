package com.practicecoding.dentalai.screen.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.screen.SearchSection
import com.practicecoding.dentalai.ui.theme.darkBlue
import com.practicecoding.dentalai.ui.theme.lightBlue
import kotlinx.coroutines.delay

@Composable
fun ScanTeethScreen(navHostController: NavHostController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBlue)
    ) {
        TopSection()
        ScanTeethSection()
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedSteps()
    }
}

@Composable
fun TopSection() {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
            .background(darkBlue)
            .padding(vertical = 16.dp, horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.profileimage),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(50))
                )
            }
            SearchSection()
        }
    }
}

@Composable
fun ScanTeethSection() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Scan Teeth",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .border(width = 5.dp, color = darkBlue)
                    .clickable { /* TODO: Handle scan teeth click */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Scan Teeth",
                    modifier = Modifier.align(Alignment.Center),
                    tint = darkBlue
                )
            }
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = { /* TODO: Handle upload from gallery click */ },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .size(width = 115.dp, height = 99.dp)
,                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "Upload\nfrom\ngallery",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick = { /* TODO: Handle retake click */ },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .size(width = 115.dp, height = 59.dp)
,                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "Retake",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedSteps() {
    var currentStep by remember { mutableIntStateOf(0) }

    LaunchedEffect(currentStep) {
        delay(200)
        if (currentStep < 3) {
            delay(1000) // Adjust the delay as needed
            currentStep += 1
        }
    }
    var stepCounter by remember {
        mutableIntStateOf(-1)
    }
    var imageId by remember {
        mutableIntStateOf(0)
    }
    var isVisible by remember {
        mutableStateOf(currentStep == stepCounter)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(1000)),
                exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(1000))
            ) {


                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = stepCounter.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(48.dp))
                )
            }
            Spacer(modifier = Modifier.height(16.dp)) // Spacer between image and text
            if (currentStep >= 1) {
                imageId = R.drawable.girlimage1
                stepCounter = 1
                isVisible = stepCounter == currentStep
                AnimatedImageAndText(stepText = "Step 1- Wide Smile")
            }
            if (currentStep >= 2) {
                imageId = R.drawable.girlimage2
                stepCounter = 2
                isVisible = stepCounter == currentStep
                AnimatedImageAndText(stepText = "Step 2- Capture your top teeth")

            }
            if (currentStep >= 3) {
                imageId = R.drawable.girlimage3
                stepCounter = 3
                isVisible = stepCounter == currentStep
                AnimatedImageAndText(stepText = "Step 3- Capture your bottom teeth")

            }
        }
    }

}


@Composable
fun AnimatedImageAndText(stepText: String) {
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    ) {
        Box(
            modifier = Modifier
                .background(darkBlue, RoundedCornerShape(20.dp))
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stepText,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )

                Icon(
                    painter = painterResource(id = R.drawable.confirmed),
                    contentDescription = "Completed",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 4.dp), tint = Color.White
                )
            }

        }

    }
    Spacer(modifier = Modifier.height(12.dp)) // Spacer between text entries
}