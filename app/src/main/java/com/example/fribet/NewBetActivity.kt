package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_friend_profile.*
import kotlinx.android.synthetic.main.activity_new_bet.*


class NewBetActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_bet)
        val betDescription = findViewById<EditText>(R.id.description)
        val betAmount = findViewById<EditText>(R.id.amount)
        val clicked = intent.getStringExtra("userId")
        Firestore.instance.getUserByUsername(clicked){userId ->
            Firestore.instance.getUserByUserId(userId.toString()){ user ->
                confirm.setOnClickListener {
                    //Firestore.instance.addBet(UserRepository.instance.currentUserId.toString(), user.userId.toString(), false, betAmount.text.toString(), betDescription.text.toString())
                }
            }
        }
    }
}