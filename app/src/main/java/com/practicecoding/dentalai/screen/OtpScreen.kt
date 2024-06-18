package com.practicecoding.dentalai.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.Screens
import com.practicecoding.dentalai.data.Resource
import com.practicecoding.dentalai.ui.theme.Crimson
import com.practicecoding.dentalai.ui.theme.EggBlue
import com.practicecoding.dentalai.utils.showMsg
import com.practicecoding.dentalai.viewmodel.AuthEvent
import com.practicecoding.dentalai.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpScreen(
    mobileNumber: String,name:String, navHostController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),

    ) {
    val otpLength = 6
    val otp = remember { List(otpLength) { mutableStateOf(TextFieldValue("")) } }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current
    var isDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val isButtonEnabled = otp.all { it.value.text.isNotEmpty() }
    var countdown by remember { mutableStateOf(60) }
    var isResendEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = countdown) {
        if (countdown > 0) {
            delay(1000L)
            countdown--
        } else {
            isResendEnabled = true
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        color = EggBlue
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(370.dp)
                    .background(EggBlue),
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                ), colors = CardColors(Color.White, Color.White, Color.White, Color.White)
            ) {
                IconButton(
                    onClick = { navHostController.popBackStack() },
                    modifier = Modifier.padding(start = 10.dp, top = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back arrow",
                        modifier = Modifier.size(28.dp),
                        tint = Color.Black
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.report),
                    contentDescription = "",
                    modifier = Modifier
                        .align(
                            Alignment.Start
                        )
                        .padding(start = 10.dp)
                        .size(100.dp)
                )
                Text(
                    text = "Login to unlock",
                    color = Color.Black,
                    fontSize = 36.sp,
                    lineHeight = 38.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 15.dp, top = 10.dp)
//                        .padding(top = 10.dp, bottom = 24.dp)
//                        .align(Alignment.CenterHorizontally),
                )
                Text(
                    text = "Unlock this report and embark first step towards better and health",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 15.dp, top = 20.dp)
//                        .padding(start = 20.dp, end = 20.dp, bottom = 8.dp),
                )
                Text(
                    text = "OTP sent to: $mobileNumber",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 15.dp, top = 20.dp)
//                        .padding(start = 20.dp, end = 20.dp, bottom = 8.dp),
                )
                Spacer(modifier = Modifier.height(15.dp))


            }
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "Enter OTP",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.height(35.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                otp.forEachIndexed { index, textFieldValue ->
                    OutlinedTextField(
                        value = textFieldValue.value,
                        onValueChange = { newValue ->
                            if (newValue.text.length <= 1) {
                                textFieldValue.value = newValue
                                when {
                                    newValue.text.isNotEmpty() && index < otpLength - 1 -> {
                                        focusRequesters[index + 1].requestFocus()
                                    }

                                    newValue.text.isEmpty() && index > 0 -> {
                                        focusRequesters[index - 1].requestFocus()
                                    }
                                }
                            }
                        },
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                if (index < otpLength - 1) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            },
                            onDone = {
                                localFocusManager.clearFocus()
                            }
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .width(50.dp)
                            .focusRequester(focusRequesters[index]),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedTextColor = Color.White,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledTextColor = Color.White,
                            unfocusedPlaceholderColor = Color.Transparent,
                            disabledIndicatorColor = Crimson,
                            focusedIndicatorColor = Crimson
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = if (isResendEnabled) "Resend OTP" else "Resend OTP in $countdown seconds",
                    color = Crimson,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable {
                            if (isResendEnabled) {
                            } else {
                            }
                        }
                        .weight(1f).padding(start=15.dp)
                )
                Text(
                    text = "Edit Phone Number",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "phonenumberlogin",
                            value = mobileNumber
                        )
                        navHostController.navigate(Screens.LoginScreen.route) {
                            popUpTo(Screens.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }.padding(end=15.dp)
                )
            }

            // TextField for entering mobile number
            Spacer(modifier = Modifier.height(55.dp))


            // Button to submit
            Button(
                modifier = Modifier
                    .size(width = 300.dp, height = 50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    val otpString = otp.joinToString(separator = "") { it.value.text }
                    scope.launch(Dispatchers.IO) {
                        viewModel.onEvent(AuthEvent.OtpCode(otpString,context,navHostController,name, mobileNumber))

                    }
                },
                enabled = isButtonEnabled,
                colors = ButtonColors(
                    disabledContainerColor = Color.Transparent,
                    containerColor = Crimson,
                    contentColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Submit",
                    color = if (isButtonEnabled) Color.White else Color.Transparent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }


    }

}