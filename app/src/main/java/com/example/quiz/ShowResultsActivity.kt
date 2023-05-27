package com.example.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShowResultsActivity:AppCompatActivity() {

    private lateinit var listAdapter: ListAdapter


    @SuppressLint("StaticFieldLeak")
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_results)
        setupRecyclerView()
    }




    private fun setupRecyclerView() {
        val rvList = findViewById<RecyclerView>(R.id.rv_list)
        with(rvList) {
            listAdapter = ListAdapter(Result.parsedList)
            adapter = listAdapter
        }

    }

}