package com.example.fribet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_bet_request.*

class BetRequestActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_request)
        val clicked = intent.getStringExtra("betId")
        Firestore.instance.getBetById(clicked){returnedBet ->
            BetRepository.instance.singleBet = returnedBet
            val bet = BetRepository.instance.singleBet
            userText.text = bet[0].playerSending + " has invited you!"
            betAmount.text = "Amount: " + bet[0].amount
            betDescription.text = bet[0].description
            betAccept.setOnClickListener{
                Firestore.instance.acceptBet(clicked)
                val intent = Intent(this, MyBetsActivity::class.java)
                startActivity(intent)
                finish()
            }
            betDecline.setOnClickListener{
                Firestore.instance.deleteBetById(clicked)
                val intent = Intent(this, MyBetsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }




    }
}