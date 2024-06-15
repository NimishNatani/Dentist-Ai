package com.practicecoding.dentalai.screen.category

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.practicecoding.dentalai.general.SpeechBubbleShapeLeft
import com.practicecoding.dentalai.general.SpeechBubbleShapeRight
import com.practicecoding.dentalai.screen.HeaderSection
import com.practicecoding.dentalai.ui.theme.darkBlue
import com.practicecoding.dentalai.ui.theme.lightBlue
import com.practicecoding.dentalai.viewmodel.ChatViewModel

@Composable
fun SymptomsTracker(viewModel: ChatViewModel = hiltViewModel()) {
    val chatState = viewModel.chatState.collectAsState().value
    var prompt by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBlue)
    ) {
        HeaderSection()
        Column(
            modifier = Modifier
                .background(lightBlue)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(White)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp, horizontal = 50.dp)
            ) {
                Text(
                    text = "Symptoms Tracker",
                    color = Color(0xFF4A5AFF),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        3.dp, darkBlue,
                        RoundedCornerShape(15.dp)
                    )
                    .background(White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp)
                        .background(White),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        reverseLayout = true
                    ) {
                        itemsIndexed(chatState.chatList) { index, chat ->
                            if (chat.isFromUser) {
                                UserChatItem(
                                    prompt = chat.prompt
                                )
                            } else {
                                ModelChatItem(response = chat.prompt)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp, start = 14.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            modifier = Modifier
                                .weight(1f)
                                ,
                            value = prompt,
                            onValueChange = {
                                prompt = it
                            },
                            placeholder = {
                                Text(text = "Type a prompt",color= White)
                            },
                            textStyle = TextStyle(color = White, fontSize = 16.sp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = darkBlue,
                                unfocusedContainerColor = darkBlue,
                                disabledContainerColor = darkBlue,
                                unfocusedLabelColor = White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Transparent,
                                disabledLabelColor = White
                            ),
                            shape = RoundedCornerShape(4.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    viewModel.sendPrompt(prompt)
//                            uriState.update { "" }
                                    prompt = ""
                                },
                            imageVector = Icons.AutoMirrored.Rounded.Send,
                            contentDescription = "Send prompt",
                            tint = darkBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserChatItem(prompt: String) {
    Column(
        modifier = Modifier
            .padding(start = 100.dp, bottom = 8.dp, top = 8.dp)
            .clip(SpeechBubbleShapeRight())
            .background(lightBlue)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(start = 16.dp, bottom = 20.dp,end=16.dp,top=6.dp),
            text = prompt,
            color = Black,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold

        )

    }
}

@Composable
fun ModelChatItem(response: String) {
    Column(
        modifier = Modifier
            .padding(end = 100.dp, bottom = 10.dp)
            .clip(SpeechBubbleShapeLeft())
            .background(
                darkBlue
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(start = 20.dp, end=20.dp,bottom = 20.dp,top=8.dp)
                .background(darkBlue),
            text = response,
            fontSize = 17.sp,
            color = White,
            fontWeight = FontWeight.SemiBold
        )

    }
}
