package com.example.fribet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var email = findViewById<EditText>(R.id.emailReg)
        var pass = findViewById<EditText>(R.id.passReg)
        var passRepeat = findViewById<EditText>(R.id.passRepeatReg)
        var username = findViewById<EditText>(R.id.usernameReg)

        registerButton.setOnClickListener(){view ->
            if(pass.text.toString() == passRepeat.text.toString()){
                registerAccount(view, email.text.toString(), pass.text.toString(), username.text.toString())
            }
            else
                Log.d("asd","error, not same pw")
        }
    }
    fun registerAccount(view: View, email: String, pass: String, username: String){
        showMessage(view,"Registering account...")
        fbAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult>{task ->
                if(task.isSuccessful){
                    Firestore.instance.addUser(email, username)
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    showMessage(view,"Error: ${task.exception?.message}")
                }
            })
    }
    fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}