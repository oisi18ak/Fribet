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


class LoggedInActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
        Firestore.instance.readUserId()
        var user = UserRepository.instance.currentUserId
        Log.d("asd", user)
        //Firestore.instance.getUserByUsername("simon")

        Firestore.instance.getUnacceptedBets{unacceptedBetsList ->
            BetRepository.instance.listOfUnacceptedBets = unacceptedBetsList
            //Log.d("asd","${BetRepository.instance.listOfUnacceptedBets}")
        }
        var btnLogOut = findViewById<Button>(R.id.btnLogout)
        var btnSetting = findViewById<Button>(R.id.settingsButton)
        var btnMyBets = findViewById<Button>(R.id.myBetsButton)
        var btnAllBets = findViewById<Button>(R.id.betsButton)
        var btnFriends = findViewById<Button>(R.id.friendsButton)

        btnSetting.setOnClickListener{
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        btnLogOut.setOnClickListener{ view ->
            showMessage(view, "Logging Out...")
            signOut()
        }

        btnMyBets.setOnClickListener{
            var intent = Intent(this, MyBetsActivity::class.java)
            startActivity(intent)
        }

        btnFriends.setOnClickListener{
            var intent = Intent(this, FriendsListActivity::class.java)
            startActivity(intent)
        }

        btnAllBets.setOnClickListener{
            var intent = Intent(this, AllBetsActivity::class.java)
            startActivity(intent)
        }

        fbAuth.addAuthStateListener {
            if(fbAuth.currentUser == null){
                this.finish()
            }
        }
    }

    fun signOut(){
        fbAuth.signOut()

    }

    fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}