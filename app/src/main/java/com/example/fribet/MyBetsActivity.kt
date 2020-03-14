package com.example.fribet

import android.os.Bundle
import android.os.PersistableBundle
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
        setContentView(R.layout.activity_my_bets)

        var listView = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter<Bets>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            BetRepository().listOfBets
        )
        listView.adapter = adapter

    }
}