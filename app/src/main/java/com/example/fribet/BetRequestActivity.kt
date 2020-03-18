package com.example.fribet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_bet_request.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class BetRequestActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_request)
        val clicked = intent.getStringExtra("betId")
        Firestore.instance.getBetById(clicked)
        Log.d("asd",clicked.toString())
        val bet = BetRepository.instance.singleBet
        Log.d("asd","${bet}")
        //userText.text = bet[0].playerSending


    }
}