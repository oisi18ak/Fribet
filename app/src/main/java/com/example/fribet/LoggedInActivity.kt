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
import kotlinx.android.synthetic.main.activity_friend_profile.*
import kotlinx.android.synthetic.main.activity_logged_in.*
import kotlinx.android.synthetic.main.activity_logged_in.losses
import kotlinx.android.synthetic.main.activity_logged_in.totalBets
import kotlinx.android.synthetic.main.activity_logged_in.wins


class LoggedInActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
        Firestore.instance.readUserId()
        var userId = UserRepository.instance.currentUserId
        if (userId != null) {
            Firestore.instance.getUserByUserId(userId.toString()){user ->
                usernameText.text = user.username
                totalBets.text = "Total bets: " + user.totalBets.toString()
                wins.text = "Wins: " + user.wins.toString()
                losses.text = "Losses: " + user.losses.toString()
                balance.text = "Balance: " + user.totalBalance.toString()
            }
        }

        //Firestore.instance.getUserByUsername("simon")
        Firestore.instance.getUnacceptedBets{unacceptedBetsList ->
            BetRepository.instance.listOfUnacceptedBets = unacceptedBetsList
            //Log.d("asd","${BetRepository.instance.listOfUnacceptedBets}")
        }
        Firestore.instance.getAllBets { allBets ->
            Log.d("checkAllBets", "${BetRepository.instance.listOfBets}")
        }







       // Firestore.instance.getUserByUsername("simon"){userId ->
        //    Firestore.instance.addFriend(userId!!)
        //}

        Firestore.instance.getFriendList { listOfFriends ->
            Log.d("FriendList", "this is your list of friends id: ${listOfFriends}")
        }
        //Firestore.instance.addUserAsFriend("simon")






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