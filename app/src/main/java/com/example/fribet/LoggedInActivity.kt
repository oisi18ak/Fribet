package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class LoggedInActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
        Firestore.instance.readUserId()
        Log.d("adinaSuger", "Den kom hit iaf")
        Firestore.instance.addBet(UserRepository.instance.currentUserId.toString(),
            "MFYbROlCV4fzMLKiWiiDBzS22lk2",true)
        Firestore.instance.getAllAcceptedBets()

        var btnLogOut = findViewById<Button>(R.id.btnLogout)
        var btnSetting = findViewById<Button>(R.id.settingsButton)

        btnSetting.setOnClickListener{
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        btnLogOut.setOnClickListener{ view ->
            showMessage(view, "Logging Out...")
            signOut()
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