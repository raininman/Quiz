package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat


class QuestionsActivity : AppCompatActivity() {
    private var currentQuestionId = -1
    private var selectedAnswers = mutableMapOf<Int, String>()

    private val originalOptionTextColor = Color.parseColor("#4A4A4A")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        val questions = Question.parsedList.shuffled()

        val tvOption1 = findViewById<TextView>(R.id.tvOption1)
        val tvOption2 = findViewById<TextView>(R.id.tvOption2)
        val tvOption3 = findViewById<TextView>(R.id.tvOption3)
        val tvOption4 = findViewById<TextView>(R.id.tvOption4)
        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val btnAnswerSubmit = findViewById<Button>(R.id.btnAnswerSubmit)


        val allOptions = arrayListOf(tvOption1, tvOption2, tvOption3, tvOption4)



        fun changeQuestion() {
            Log.d("changeAct","ChangeQuest ${questions.size}")
            // Go to results screen if it's the end of questions Array
            if (currentQuestionId + 1 == questions.size) {
                return startActivity(
                    Intent(
                        this,
                        ResultActivity::class.java,
                    )
                )
            }
            currentQuestionId += 1

            val question = questions[currentQuestionId]

            tvQuestion.text = question.text
            progressBar.progress = currentQuestionId

            tvOption1.text = question.option1
            tvOption2.text = question.option2
            tvOption3.text = question.option3
            tvOption4.text = question.option4
        }

        fun resetOptionsColor() {
            for (option in allOptions) {
                option.setTextColor(originalOptionTextColor)
                option.typeface = Typeface.DEFAULT
                option.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.default_option_border
                )
            }
        }

        for (option in allOptions) {
            option.setOnClickListener {
                resetOptionsColor() // To prevent multi-selection

                option.setTextColor(Color.parseColor("#417EFF"))
                option.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.selected_option_border
                )
                selectedAnswers[currentQuestionId] = option.text.toString()
            }
        }

        changeQuestion()

        btnAnswerSubmit.setOnClickListener {
            if (selectedAnswers.containsKey(currentQuestionId)) {
                if (currentQuestionId + 1 == questions.size) {
                    for ((questionIndex, answer) in selectedAnswers) {
                        if (questions[questionIndex].correctAnswer == answer) {
                            Result.score += 1
                        }
                    }
                }
                changeQuestion()
                resetOptionsColor()
            }
        }

    }

}