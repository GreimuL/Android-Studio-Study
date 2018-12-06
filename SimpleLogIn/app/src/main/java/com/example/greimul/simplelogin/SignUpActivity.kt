package com.example.greimul.simplelogin

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception

class SignUpActivity:AppCompatActivity() {

    var accountData:List<String> = ArrayList<String>()

    var pwshow:Boolean = false
    var idCheck:Boolean = false

    val path = Environment.getExternalStorageDirectory()
    val directory = File(path, "SimpleLogin")
    val getfile = File(directory, "AccountData.txt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        accountData = getfile.readLines()
        checkDupli.setOnClickListener{
            var checkD:Boolean = true
            if(signUpID.text.toString()==""){
                Toast.makeText(this,"Please write ID",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            accountData.forEach{
                val (dataID,dataPW) = it.split(' ')
                if(dataID==signUpID.text.toString()){
                    checkD = false
                }
            }
            if(checkD){
                idCheck = true
                Toast.makeText(this,"Accept",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"ID already exists",Toast.LENGTH_SHORT).show()
            }
        }
        signUpCheck.setOnClickListener{
            if(signUpPW.text.toString()==""){
                Toast.makeText(this,"Please write PW",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(idCheck == false){
                Toast.makeText(this,"Please, ID Check First",Toast.LENGTH_SHORT).show()
            }
            else {
                getfile.appendText(signUpID.text.toString()+' '+signUpPW.text.toString()+"\n")
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
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
    fun File.readLines():List<String>{
        var tmp = ArrayList<String>()
        BufferedReader(InputStreamReader(FileInputStream(this))).forEachLine ({ tmp.add(it) })
        return tmp
    }

}