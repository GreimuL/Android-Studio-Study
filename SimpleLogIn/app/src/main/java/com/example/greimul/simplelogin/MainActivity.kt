package com.example.greimul.simplelogin

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.audiofx.EnvironmentalReverb
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
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    var pwshow:Boolean = false
    var accountData:List<String> = ArrayList<String>()

    val path = Environment.getExternalStorageDirectory()
    val directory = File(path, "SimpleLogin")
    val getfile = File(directory, "AccountData.txt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        loadAccount()
        val enCry = AccountEncrypt()
        logIn.setOnClickListener {
            accountData.forEach {
                val (dataID,dataPW) =  it.split(' ')
                if(dataID == enCry.encrypt(idInput.text.toString())){
                    if(dataPW == enCry.encrypt(pwInput.text.toString())){
                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,LoginActivity::class.java)
                        intent.putExtra("ID",idInput.text.toString())
                        startActivity(intent)
                        finish()
                        return@setOnClickListener
                    }
                    else{
                        Toast.makeText(this, "Please check your id/pw", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
            }
            Toast.makeText(this, "Please check your id/pw", Toast.LENGTH_SHORT).show()
        }
        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        checkBox.setOnClickListener {
            if (pwshow == true) {
                pwshow = false
                pwInput.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                pwshow = true
                pwInput.transformationMethod = HideReturnsTransformationMethod()
            }
        }
    }
    fun init(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return;
        }
        var ck:Int = checkCallingOrSelfPermission(permission)
        if(ck== PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(permission),0)
        }
        try {
            directory.mkdirs()
            if (!getfile.exists()) {
                FileOutputStream(getfile)
            }
        }
        catch(e:Exception){
            Toast.makeText(this,"Initial setting failed",Toast.LENGTH_SHORT)
        }
    }
    fun loadAccount(){
        try {
            accountData = getfile.readLines()
            Toast.makeText(this, "AccountData load Success", Toast.LENGTH_SHORT).show()
        }
        catch(e:Exception){
            Toast.makeText(this, "AccountData load Failed\nPlease allow permission\nAnd restart app", Toast.LENGTH_LONG).show()
        }
    }

    fun File.readLines():List<String>{
        var tmp = ArrayList<String>()
        BufferedReader(InputStreamReader(FileInputStream(this))).forEachLine ({ tmp.add(it) })
        return tmp
    }
}
