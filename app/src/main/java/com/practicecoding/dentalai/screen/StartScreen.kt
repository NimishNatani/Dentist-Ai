package com.practicecoding.dentalai.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.Screens
import com.practicecoding.dentalai.general.Gradient
import com.practicecoding.dentalai.ui.theme.darkBlue
import kotlinx.coroutines.delay

@Composable
fun StartScreen(navHostController: NavHostController) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(200)
        isVisible = true
    }
    Box(modifier = Modifier.background(Gradient.gradientMain)) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ), exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
                    .padding(bottom = 30.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ribon),
                    contentDescription = "ribbon",
                    modifier = Modifier
                        .size(height = 300.dp, width = 200.dp)
                        .align(Alignment.Start)  // Ensure image alignment
                        .padding(0.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Start your\nAI.Dental\nJourney",
                    fontSize = 40.sp,
                    modifier = Modifier.padding(start = 25.dp),
                    color = Color.Black,
                    lineHeight = 44.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = { navHostController.navigate(Screens.LoginScreen.route) },
                    modifier = Modifier.padding(start = 25.dp),
                    colors = ButtonDefaults.buttonColors(darkBlue)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Start To Join",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.right_arrow),
                            contentDescription = "right_arrow",
                            tint = Color.White
                        )
                    }
                }

            }
        }
    }
}