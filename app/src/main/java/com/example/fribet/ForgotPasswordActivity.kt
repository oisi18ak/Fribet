package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        var email = findViewById<EditText>(R.id.forgotEmail)
        var button = findViewById<Button>(R.id.sendButton)
        button.setOnClickListener(){
            passwordReset(email.text.toString())
        }
    }

    fun passwordReset(email: String){
        fbAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this, OnCompleteListener<Void>{ task ->
                if(task.isSuccessful){
                    Log.d("asd", "Successfull")
                }else{
                    Log.d("asd", "Not successfull")
                }
            })
    }
}