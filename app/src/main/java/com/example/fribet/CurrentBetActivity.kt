package com.example.fribet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_bet_request.*
import kotlinx.android.synthetic.main.activity_bet_request.betDescription
import kotlinx.android.synthetic.main.activity_current_bet.*

class CurrentBetActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_bet)
        Log.d("asd","Kommer in i currentbetactivitty")
        val clicked = intent.getStringExtra("betId")
        Firestore.instance.getBetById(clicked){returnedBet ->
            BetRepository.instance.singleBet = returnedBet
            val bet = BetRepository.instance.singleBet
            VS.text = bet[0].playerSending + " VS " + bet[0].playerReceiving
            //user1Text.text = bet[0].playerSending
            //user2Text.text = bet[0].playerReceiving
            betDescription.text = bet[0].description
            newBetButton.setOnClickListener{

            }
        }




    }
}