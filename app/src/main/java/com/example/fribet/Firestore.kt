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
    fun addBet(playerSending: String, playerReceiving: String,accepted:Boolean){
        val newBet = Bets(playerSending, playerReceiving,"",0,accepted)
        db.collection("Bets").add(newBet)
    }

    fun getAllAcceptedBets(){

        db.collection("Bets")
            .whereEqualTo("accepted",true)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    BetRepository.instance.listOfAcceptedBets.add(document.toObject(Bets::class.java))
                    Log.d("betsuccess", "${document.id} => ${document.data}")
                }
                Log.d("blablabla",BetRepository.instance.listOfAcceptedBets.toString())
            }
            .addOnFailureListener { exception ->
                Log.d("betfail", "Error getting documents: ", exception)
            }
    }

    fun getAllBets(){
        val betRef = db.collection("Bets").get()
        Log.d("hej2","outside")
        betRef.addOnCompleteListener{
            if(it.isSuccessful) {
                Log.d("hej1","Inside success")
                BetRepository.instance.listOfBets = it.result?.toObjects(Bets::class.java) as MutableList<Bets>
                Log.d("theBets: ", BetRepository.instance.listOfBets.toString())
            }
            else{
                Log.d("Errors: ", it.exception.toString())
            }
        }

    }























}