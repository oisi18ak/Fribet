package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_logged_in.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity  : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)










        Firestore.instance.getFriendList { friendList ->
            Log.d("friendListTesting", "The friend list is currently: ${friendList}")
        }




        setContentView(R.layout.activity_settings)
        changeButton.setOnClickListener{
            var intent = Intent(this, ChangePasswordActivityActivity::class.java)
            startActivity(intent)
        }
        deleteButton.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("Delete account")
                .setMessage("Do you really want to delete?")
                .setPositiveButton("Yes") {
                        dialog, whichButton ->
                        fbAuth.currentUser?.delete()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                }.setNegativeButton(
                    "No"
                ) { dialog, whichButton ->
                    // Do not delete it.
                }.show()
        }

    }

}