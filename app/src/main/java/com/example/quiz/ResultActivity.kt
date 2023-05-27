package com.example.quiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.quiz.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ResultActivity: AppCompatActivity() {

    @SuppressLint("StaticFieldLeak")
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val tvName = findViewById<TextView>(R.id.tvNameResult)
        val tvScore = findViewById<TextView>(R.id.tvScore)
        val btnFinish = findViewById<Button>(R.id.btnFinish)

        tvName.text = "${Question.name}"
        tvScore.text = "Ваш результат: ${Result.score}"

        btnFinish.setOnClickListener {
            addScore(hashMapOf("name" to Question.name, "score" to Result.score))
            Result.scores.add(hashMapOf("name" to Question.name, "score" to Result.score))
            Result.score = 0
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java,
                )
            )

        }

    }

    fun addScore(scoreMap:HashMap<String,Any>){
        db.collection("score")
            .add(scoreMap)
            .addOnSuccessListener { documentReference ->
                Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error adding document", e)
            }

    }
}