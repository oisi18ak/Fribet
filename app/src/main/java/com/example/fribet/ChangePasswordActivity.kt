package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fribet.MainActivity
import com.example.fribet.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.changeButton

class ChangePasswordActivityActivity  : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        changeButton.setOnClickListener{
            if(repeatPassword.text.toString() == repeatPassword.text.toString()){
                fbAuth.currentUser?.updatePassword(repeatPassword.text.toString())
                var intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            else
                Log.d("asd","error, not same pw")

        }
    }
}
