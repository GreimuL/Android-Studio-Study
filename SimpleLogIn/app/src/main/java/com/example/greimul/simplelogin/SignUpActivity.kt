package com.example.greimul.simplelogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity:AppCompatActivity() {

    var pwshow:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        checkDupli.setOnClickListener{

        }
        signUpCheck.setOnClickListener{

        }
        backBt.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        checkBox2.setOnClickListener{
            if(pwshow == true){
                pwshow = false
                signUpPW.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            else{
                pwshow = true
                signUpPW.transformationMethod = HideReturnsTransformationMethod()
            }
        }
    }

}