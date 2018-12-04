package com.example.greimul.simplelogin

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.InputType
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var pwshow:Boolean = false
    var accountData:List<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        dataArrange()
        logIn.setOnClickListener{

        }
        signUp.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        checkBox.setOnClickListener{
            if(pwshow == true){
                pwshow = false
                pwInput.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            else{
                pwshow = true
                pwInput.transformationMethod = HideReturnsTransformationMethod()
            }
        }
    }
    fun init(){
        var os = openFileOutput("AccountData.txt", Context.MODE_PRIVATE)
        os.write("asdf".toByteArray())
        os.close()
    }
    fun dataArrange(){
        val directory = File(this.filesDir,"SimpleLogin")
        val file = File(directory,"AccountData.txt")
        file.createNewFile()
        accountData = file.readLines()
    }
    fun File.readLines():List<String>{
        val result = ArrayList<String>()
        BufferedReader(InputStreamReader(FileInputStream(this))).forEachLine({
            result.add(it)
        })
        return result
    }
}
