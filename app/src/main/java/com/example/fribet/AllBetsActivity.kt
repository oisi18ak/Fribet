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

class AllBetsActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onStart() {
        super.onStart()
        Firestore.instance.getAllBets { allBets ->
            BetRepository.instance.listOfBets = allBets
            var mutableList = mutableListOf<String>()
            mutableList.add("Test")
            for (bet in BetRepository.instance.listOfBets)
                bet.playerReceiving?.let { mutableList.add(it + " VS " + bet.playerSending) }
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
        var mutableList = mutableListOf<String>()
        for (bet in BetRepository.instance.listOfBets) {
            bet.playerReceiving?.let { mutableList.add(it + " VS " + bet.playerSending) }
        }
        setContentView(R.layout.activity_all_bets)
        var listView = findViewById<ListView>(R.id.listView)
        Log.d("asd", "${mutableList}")
        Log.d("asd", "${BetRepository.instance.listOfBets}")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            mutableList
        )
        listView.adapter = adapter

        listView.setOnItemClickListener { adapter, view, position, id ->
            val clickedToDo = BetRepository.instance.listOfBets
            val betId = clickedToDo[position-1].betID
            val intent = Intent(this, CurrentBetActivity::class.java)
            intent.putExtra("betId", betId)
            startActivity(intent)
        }
    }
}