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
import kotlinx.android.synthetic.main.activity_all_bets.*
import kotlinx.android.synthetic.main.activity_all_bets.listView
import kotlinx.android.synthetic.main.activity_my_bets.*

class FriendsListActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onStart() {
        super.onStart()
        Firestore.instance.getAllBets{allBets ->
            BetRepository.instance.listOfBets = allBets
            var mutableList = mutableListOf<String>()
            //mutableList.add("test")
            for (bet in BetRepository.instance.listOfBets)
                bet.playerReceiving?.let { mutableList.add(it + " Invited you!") }
            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mutableList
            )
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)
        var mutableList = mutableListOf<String>()
        for (bet in BetRepository.instance.listOfBets)
            bet.playerReceiving?.let { mutableList.add(it) }
        var listView = findViewById<ListView>(R.id.listView)
        Log.d("asd","${mutableList}")
        Log.d("asd","${BetRepository.instance.listOfBets}")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            mutableList
        )
        listView.adapter = adapter

        listView.setOnItemClickListener{ adapter, view, position, id ->
            val clickedToDo = BetRepository.instance.listOfBets
            val betId = clickedToDo[position].betID
            val intent = Intent(this, BetRequestActivity::class.java)
            intent.putExtra("betId", betId)
            startActivity(intent)
        }
    }
}