package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.activity_friends_list.addButton

class AddFriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        var username = findViewById<EditText>(R.id.searchText)
        addButton.setOnClickListener {
            Log.d("asd", username.toString())
            Firestore.instance.getUserByUsername(username.toString()){userId ->
                var user = userId
                if(user == null){
                    searchText.error = "No user found with that name!"
                }
                else{
                    AlertDialog.Builder(this)
                    .setTitle("Found user!")
                    .setMessage("Do you want to add?")
                    .setPositiveButton("Yes") {
                            dialog, whichButton ->
                        Firestore.instance.addFriend(user)
                        val intent = Intent(this, FriendsListActivity::class.java)
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
    }
}