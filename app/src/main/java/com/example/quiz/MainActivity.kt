package com.example.quiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getQuestions()
        getScores()

        val btnStartQuiz = findViewById<Button>(R.id.btnStartQuiz)
        val btnShowResults = findViewById<Button>(R.id.btnShowResults)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvNameParent = findViewById<TextInputLayout>(R.id.tvNameParent)


        btnStartQuiz.setOnClickListener {
            if (tvName.text.toString().isEmpty()) {
                tvNameParent.error = "Введите имя"
            } else {
                Question.parsedList = parseQuestions(Question.list)
                Question.name = tvName.text.toString()
                startActivity(
                    Intent(
                        this,
                        QuestionsActivity::class.java,
                    )
                )
            }
        }

        btnShowResults.setOnClickListener {
            Result.parsedList = parseScores(Result.scores)
                startActivity(
                    Intent(
                        this,
                        ShowResultsActivity::class.java,
                    )
                )
        }

    }


    val question = hashMapOf(
        "id" to 4,
        "text" to "To which programming language does this mascot belongs?",
        "option1" to "Java",
        "option2" to "Flutter",
        "option3" to "Kotlin",
        "option4" to "Swift",
        "correctAnswer" to "Kotlin"
    )

    @SuppressLint("StaticFieldLeak")
    val db = Firebase.firestore

    private fun getQuestions() {
        if (Question.list.isEmpty()) {
            db.collection("quiz")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("Firebase", "${document.id} => ${document.data}")
                        Question.list.add(document.data)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Firebase", "Error getting documents.", exception)
                }

        }
    }

    private fun getScores() {
        if (Result.scores.isEmpty()) {
            db.collection("score")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("Firebase", "${document.id} => ${document.data}")
                        Result.scores.add(document.data)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Firebase", "Error getting documents.", exception)
                }

        }
    }

    private fun parseScores(list: List<Map<String, Any>>): List<Result> {
        val array = mutableListOf<Result>()
        var score: Any? = 0
        var name: Any? = ""
        for (document in list) {
            for (key in document.keys) {
                when (key) {
                    "score" -> {
                        score = document[key].toString().toInt()
                    }
                    "name" -> {
                        name = document[key]
                    }
                    else -> {
                        Log.d("Firebase", "Error key: $key")
                    }
                }
            }
            array.add(
                Result(
                    name as String,
                    score as Int
                )
            )
        }
        return array
    }



    private fun parseQuestions(list: List<Map<String, Any>>): List<Question> {
        val array = mutableListOf<Question>()
        var id: Any? = 0L
        var option1: Any? = ""
        var option2: Any? = ""
        var option3: Any? = ""
        var option4: Any? = ""
        var text: Any? = ""
        var correctAnswer: Any? = ""

        for (document in list) {
            for (key in document.keys) {
                when (key) {
                    "id" -> {
                        id = document[key].toString().toInt()
                    }
                    "option1" -> {
                        option1 = document[key]
                    }
                    "option2" -> {
                        option2 = document[key]
                    }
                    "option3" -> {
                        option3 = document[key]
                    }
                    "option4" -> {
                        option4 = document[key]
                    }
                    "text" -> {
                        text = document[key]
                    }
                    "correctAnswer" -> {
                        correctAnswer = document[key]
                    }
                    else -> {
                        Log.d("Firebase", "Error key: $key")
                    }
                }
            }
            array.add(
                Question(
                    id as Int,
                    text as String,
                    option1 as String,
                    option2 as String,
                    option3 as String,
                    option4 as String,
                    correctAnswer as String
                )
            )
        }
        return array
    }
}



