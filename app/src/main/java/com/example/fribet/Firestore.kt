package com.example.fribet

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
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
    val firebaseAuth = FirebaseAuth.getInstance()



    fun addUser(email:String, username: String){
        firebaseAuth.currentUser?.reload()
        val userId = firebaseAuth.currentUser?.uid
        Log.d("idInAddUser",userId)
        val newUser = User(username,email,userId)
        db.collection("Users").document(userId!!).set(newUser)
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

    //fun readUserId(){
      //  val user = firebaseAuth.currentUser
        //UserRepository.instance.currentUserId = user?.uid
    //}

    

}