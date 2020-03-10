package com.example.fribet

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
}