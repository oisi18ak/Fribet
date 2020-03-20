package com.example.fribet

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
//import androidx.test.orchestrator.junit.BundleJUnitUtils.getResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.security.auth.callback.Callback

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


    fun addBet(playerSending: String, playerReceiving: String,accepted:Boolean, amount: Int, description: String){
        var newBet = Bets(accepted,amount, " ", false,description,playerReceiving,playerSending)
        val docRef = db.collection("Bets")
            .add(newBet)
        docRef.addOnSuccessListener {
            val id = it.id
            val documentReference = db.collection("Bets").document("${id}")
            documentReference.update("betID", id)
        }
    }

    fun getAllAcceptedBets(callback: (MutableList<Bets>) -> Unit){
        db.collection("Bets")
            .whereEqualTo("accepted",true)
            .get()
            .addOnSuccessListener { result ->
                var listOfBets = mutableListOf<Bets>()
                for (document in result) {
                  listOfBets.add(document.toObject(Bets::class.java))
                  //  BetRepository.instance.listOfAcceptedBets.add(document.toObject(Bets::class.java))
                    Log.d("betsuccess", "${document.id} => ${document.data}")
                }
                callback(listOfBets)
            }
            .addOnFailureListener { exception ->
                Log.d("betfail", "Error getting documents: ", exception)
            }
    }

    fun getAllPlayerBets(){
        val betRef = db.collection("Bets")
            .whereEqualTo("playerReceiving",UserRepository.instance.currentUserId)
            .get()
        betRef.addOnCompleteListener{
            if(it.isSuccessful) {
                Log.d("hej1","Inside success")
                BetRepository.instance.listOfBets = it.result?.toObjects(Bets::class.java) as MutableList<Bets>
                Log.d("testoterone","${BetRepository.instance.listOfBets}")
            }
            else{
                Log.d("Errors: ", it.exception.toString())
            }
        }
    }

    fun getAllUncompletedBets(){
        val betRef = db.collection("Bets")
            .whereEqualTo("completed",false)
            .get()
        betRef.addOnCompleteListener{
            if(it.isSuccessful) {
                BetRepository.instance.listOfUncompletedBets = it.result?.toObjects(Bets::class.java) as MutableList<Bets>
                Log.d("testoterone","${BetRepository.instance.listOfBets}")
            }
            else{
                Log.d("Errors: ", it.exception.toString())
            }
        }
    }
//Used for a users history & probably for a notifyng users that a bet has been completed
    fun getAllCompletedBets(){
        val betRef = db.collection("Bets")
            .whereEqualTo("completed",true)
            .get()
        betRef.addOnCompleteListener{
            if(it.isSuccessful) {
                BetRepository.instance.listOfCompletedBets = it.result?.toObjects(Bets::class.java) as MutableList<Bets>
                Log.d("testoterone","${BetRepository.instance.listOfBets}")
            }
            else{
                Log.d("Errors: ", it.exception.toString())
            }
        }

    }

    fun readUserId(){
        val user = firebaseAuth.currentUser
        UserRepository.instance.currentUserId = user?.uid
    }

    fun getUnacceptedBets(callback: (MutableList<Bets>) -> Unit){
        db.collection("Bets")
            .whereEqualTo("accepted",false)
            .whereEqualTo("playerReceiving",UserRepository.instance.currentUserId)
            .get()
            .addOnSuccessListener { result ->
                val unacceptedBetsList = mutableListOf<Bets>()
                for (document in result) {
                    BetRepository.instance.listOfUnacceptedBets.add(document.toObject(Bets::class.java))
                    Log.d("betsuccess2", "${document.id} => ${document.data}")
                }
                callback(unacceptedBetsList)
            }
            .addOnFailureListener { exception ->
                Log.d("betfail2", "Error getting documents: ", exception)
            }
    }

    fun getAllBets(callback: (MutableList<Bets>) -> Unit){
        db.collection("Bets")
            .get()
            .addOnSuccessListener { result ->
                val listOfBets = mutableListOf<Bets>()
                for (document in result) {
                    listOfBets.add(document.toObject(Bets::class.java))
                    BetRepository.instance.listOfBets.add(document.toObject(Bets::class.java))
                    Log.d("betsuccess", "${document.id} => ${document.data}")
                }
                callback(listOfBets)
            }
            .addOnFailureListener { exception ->
                Log.d("betfail", "Error getting documents: ", exception)
            }
    }

    fun getBetById(betId: String , callback: (MutableList<Bets>) -> Unit) {
        db.collection("Bets")
            .whereEqualTo("betID", betId)
            .get()
            .addOnSuccessListener { result ->
                val returnedBet = result.toObjects(Bets::class.java)
                callback(returnedBet)
                Log.d("betByIdSuccess", "Bet successfully received with result: ${result}")
            }
            .addOnFailureListener { e ->
                Log.d("betByIdFail", "Bet unsuccessfully received with result: ", e)
            }
    }

    fun getUserByUsername(username: String, callback: (String?) -> Unit) {
        db.collection("Users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { result ->
                val userWithUsername = result.toObjects(User::class.java)
                val userId = userWithUsername[0].userId
                callback(userId)
            }
            .addOnFailureListener {error ->
                Log.d("getUserByUsernameFail","Couldn't find a user with that username", error)
            }

    }

    fun getUserByUserId(userId: String, callback: (User) -> Unit){
        val docRef = db.collection("Users")
            .whereEqualTo("userId",userId)
            .get()
            .addOnSuccessListener {
                val user = it.toObjects(User::class.java)
                callback(user[0])
            }
            .addOnFailureListener {
                Log.d("UserNotGood", "no user by that id")
            }
    }
    

    fun deleteBetById(betId: String){
        val docRef = db.collection("Bets")
            .document(betId)
        docRef.delete()

    }

    fun addUserAsFriend(username: String){
        db.collection("Users")
            .whereEqualTo("userID",firebaseAuth.currentUser?.uid)
            .get()
            .addOnSuccessListener { result ->
                val currentUser = result.toObjects(User::class.java)
                db.collection("User")
                    .whereEqualTo("username",username)
                    .get()
                    .addOnSuccessListener { friendResult ->
                        val userToAdd = friendResult.toObjects(User::class.java)
                        currentUser[0].friends?.add(userToAdd[0].userId!!)
                    }
                    .addOnFailureListener{
                        Log.d("addUserAsFriendFail", "Something went not good, prob user doesnt exist or something")
                    }
            }
    }









}



//Used to copy and paste to morph into; uncompleted. completed. etc Bets
/*
fun getAllBets(){
    val betRef = db.collection("Bets")
        .get()
    betRef.addOnCompleteListener{
        if(it.isSuccessful) {
            BetRepository.instance.listOfBets = it.result?.toObjects(Bets::class.java) as MutableList<Bets>
            Log.d("testoterone","${BetRepository.instance.listOfBets}")
        }
        else{
            Log.d("Errors: ", it.exception.toString())
        }
    }
}
*/
