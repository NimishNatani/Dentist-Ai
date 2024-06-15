package com.practicecoding.dentalai.data.model

data class CalendarInput(
    val day:Int,
    val toDos:List<String> = emptyList()
)