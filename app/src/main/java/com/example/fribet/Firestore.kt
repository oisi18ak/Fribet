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
        val testList = listOf(db.collection("Bets"))
        testList.forEach { System.out.print(it) }
    }























}