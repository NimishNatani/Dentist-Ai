package com.practicecoding.dentalai.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.Screens
import com.practicecoding.dentalai.general.Gradient
import com.practicecoding.dentalai.ui.theme.Crimson
import com.practicecoding.dentalai.ui.theme.darkBlue
import com.practicecoding.dentalai.viewmodel.AuthEvent
import com.practicecoding.dentalai.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    activity: Activity,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    var mobileNo by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    val scroll = rememberScrollState()
    LaunchedEffect(Unit) {
        isVisible = true
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    BackHandler {
        navHostController.popBackStack()
    }

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                    .animateContentSize()
                    .background(Gradient.gradientCard)
                    .padding(vertical = 70.dp, horizontal = 20.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.filledteeth),
                        contentDescription = "teeth",
                        modifier = Modifier
                            .size(90.dp)
                            .animateContentSize()

                    )
                    Text(
                        text = "Welcome!",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Crimson, modifier = Modifier.animateContentSize()

                    )
                    Text(
                        text = "Unlock a Brighter, healthier\nsmile with AI-Powered Dental\nAssistant", textAlign = TextAlign.Center,
                        color = darkBlue, modifier = Modifier
                            .animateContentSize()
                            .align(Alignment.CenterHorizontally)
                    )

                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Login",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.animateContentSize(),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Sign in to Continue", modifier = Modifier.animateContentSize(),color= Color.White)
            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                value = name,
                onValueChange = {
                   name = it
                },
                label = { Text("Enter Name", color = Color.Black) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .animateContentSize()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Black,
                    disabledLabelColor = Color.White,
                    cursorColor = darkBlue,
                ),

                )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = mobileNo,
                onValueChange = {
                   mobileNo = it
                },
                label = { Text("Enter Phone Number", color = Color.Black) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .animateContentSize()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Black,
                    disabledLabelColor = Color.White,
                    cursorColor = darkBlue,
                ),

                )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    scope.launch(Dispatchers.IO){
                        viewModel.onEvent(AuthEvent.CreateUser(mobileNo,activity, context,navHostController))
                    }

                    },
                colors = ButtonDefaults.buttonColors(Crimson),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .animateContentSize()
            ) {
                Text(text = "Log in", color = Color.White)
            }


            TextButton(
                onClick = { /* Handle forgot password */ },
                modifier = Modifier.animateContentSize()
            ) {
                Text(
                    "Forgot Password?",
                    color = Color.White,
                    modifier = Modifier.animateContentSize()
                )
            }


            TextButton(
                onClick = { /* Handle signup */ },
                modifier = Modifier.animateContentSize()
            ) {
                Text("Signup!", color = Color.White, modifier = Modifier.animateContentSize())
            }
        }

    }
}