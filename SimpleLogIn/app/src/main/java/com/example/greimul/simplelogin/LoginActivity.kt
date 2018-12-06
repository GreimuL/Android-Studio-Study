package com.example.greimul.simplelogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity:AppCompatActivity() {

    var userID:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var intent = getIntent()
        userID = intent.getStringExtra("ID")
        greetText.setText("Hello! "+userID)
        logoutBt.setOnClickListener{
            Toast.makeText(this,"Log Out",Toast.LENGTH_SHORT)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}