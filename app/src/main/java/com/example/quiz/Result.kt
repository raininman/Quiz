package com.example.quiz

class Result(
    val name:String,
    val score:Int
) {
   companion object{
       var score = 0
       var parsedList = listOf<Result>()
       var scores = mutableListOf<Map<String, Any>>()
   }
}