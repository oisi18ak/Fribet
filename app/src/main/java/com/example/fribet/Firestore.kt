package com.example.fribet

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Firestore {
    companion object{
        val instance = Firestore()
    }
    val db = FirebaseFirestore.getInstance()



    fun addUser(email:String, username: String){
        val newUser = User(username,email)
        db.collection("Users").add(newUser)
    }
    fun addBet(playerSending: String, playerReceiving: String){
        val newBet = Bets(playerSending, playerReceiving)
        db.collection("Bets").add(newBet)
    }

    fun getAllBets(){
        db.collection("Bets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("betsuccess", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("betfail", "Error getting documents: ", exception)
            }

    }























}