package com.example.fribet

import android.util.Log
//import androidx.test.orchestrator.junit.BundleJUnitUtils.getResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
//import org.junit.experimental.results.ResultMatchers.isSuccessful


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


    fun getPlayerId(){
        db.collection("Users")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result!!.documents) {
                        UserRepository.instance.playerId = documentSnapshot.id
                        Log.d("getPlayerIdId", "The players id is: ${UserRepository.instance.playerId}")
                        // here you can get the id.
                        //val user: User = documentSnapshot.toObject(User::class.java).
                        //document.toObject(user::class.java).withId(document.getId())
                        // you can apply your actions...
                    }
                }
                else {

                }
            })
    }

    fun getPlayerAcceptedBets(){
        val betRef = db.collection("Bets")
            //.whereEqualTo("playerReceiving")
            .get()
        betRef.addOnSuccessListener {

        }
    }























}