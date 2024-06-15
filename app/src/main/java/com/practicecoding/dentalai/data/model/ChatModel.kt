package com.practicecoding.dentalai.data.model

data class ChatState(
    val chatList: MutableList<Chat> = mutableListOf(
        Chat(
            "Welcome to Molar Dental assistant",
            false
        ), Chat("Hello!", false)
    ),
    val prompt: String = ""
)

data class Chat(
    val prompt: String,
    val isFromUser: Boolean
)