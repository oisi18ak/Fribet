package com.example.fribet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firestore.instance.getAllBets{allBets ->
            BetRepository.instance.listOfBets = allBets
            //Log.d("asd", "${BetRepository.instance.listOfBets}")
        }
        Firestore.instance.getUserByUsername("simon"){ userWithUsername ->
            UserRepository.instance.currentUserId = userWithUsername
            val user = UserRepository.instance.currentUserId
            //Firestore.instance.addBet(user.toString(), user.toString(),false,202, "This is the description")
        }



        setContentView(R.layout.activity_main)
        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var loginInfo = findViewById<EditText>(R.id.loginText)
        var passwordInfo = findViewById<EditText>(R.id.passText)
        var btnForgot = findViewById<Button>(R.id.forgotButton)
        btnLogin.setOnClickListener {view ->
            //signIn(view,loginInfo.text.toString(), passwordInfo.text.toString())
            signIn(view, "3@3.3", "asdfgh")
        }
        registerButton.setOnClickListener{
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        btnForgot.setOnClickListener{
            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    fun signIn(view: View, email: String, password: String){
        showMessage(view,"Authenticating...")
        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if(task.isSuccessful){
                var intent = Intent(this, LoggedInActivity::class.java)
                intent.putExtra("id", fbAuth.currentUser?.email)
                startActivity(intent)
            }else{
                showMessage(view,"Error: ${task.exception?.message}")
            }
        })

    }

    fun showMessage(view:View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}

