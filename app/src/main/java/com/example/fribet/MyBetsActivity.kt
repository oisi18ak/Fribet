package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyBetsActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mutableList = mutableListOf<String>()
        for (bet in BetRepository.instance.listOfAcceptedBets)
            bet.playerReceiving?.let { mutableList.add(it) }
        setContentView(R.layout.activity_my_bets)
        var listView = findViewById<ListView>(R.id.listView)
        Log.d("asd","${mutableList}")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            mutableList
        )
        listView.adapter = adapter

        listView.setOnItemClickListener{ adapter, view, position, id ->
            val clickedToDo = BetRepository.instance.listOfAcceptedBets
            val todoid = clickedToDo[position].playerReceiving
            val intent = Intent(this, BetRequestActivity::class.java)
            intent.putExtra("toDoId", todoid)
            startActivity(intent)
        }
    }
}