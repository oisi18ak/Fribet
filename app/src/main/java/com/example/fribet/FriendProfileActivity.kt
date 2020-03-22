package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_friend_profile.*


class FriendProfileActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_profile)
        val clicked = intent.getStringExtra("userId")
        Firestore.instance.getUserByUsername(clicked){userId ->
            Firestore.instance.getUserByUserId(userId.toString()){ user ->
                usernameText.text = user.username
                totalBets.text = "Total bets: " + user.totalBets.toString()
                wins.text = "Wins: " + user.wins.toString()
                losses.text = "Losses: " + user.losses.toString()
            }
        }
        challengeButton.setOnClickListener {
            val intent = Intent(this, NewBetActivity::class.java)
            intent.putExtra("user", clicked)
            startActivity(intent)
        }
    }
}