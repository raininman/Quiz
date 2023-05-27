package com.example.quiz

data class Question (
    val id: Int,
    val text: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctAnswer: String
) {
    companion object{
        val list = mutableListOf<Map<String, Any>>()
        var parsedList = listOf<Question>()
        var name:String = ""
    }
}