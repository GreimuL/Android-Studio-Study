package com.example.greimul.simplenotepad

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.TextView
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import java.io.FileOutputStream
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    var textView:TextView? = null
    var saveBt:Button? = null
    var noteData:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textedit)
        saveBt = findViewById(R.id.savebt)
        saveBt?.setOnClickListener(this)
    }
    override fun onClick(a : View?){
        permissionCheck()
        noteData = textView?.text?.toString()
        save(noteData)
    }
    fun permissionCheck(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return;
        }
        var ck:Int = checkCallingOrSelfPermission(permission)
        if(ck== PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(permission),0)
        }
    }
    fun save(saveData:String?){
        try {
            val path = Environment.getExternalStorageDirectory()
            val directory = File(path,"SimpleNote")
            directory.mkdirs()
            val fileout = File(directory,"SimpleNote.txt")
            fileout.createNewFile()
            if(saveData == null) {
                FileOutputStream(fileout).use {
                    it.write("".toByteArray())
                }
            }
            else {
                FileOutputStream(fileout).use {
                    it.write(saveData.toByteArray())
                }
            }
            Toast.makeText(this,"Save Success", Toast.LENGTH_SHORT).show()
        }
        catch(e:Exception){
            Toast.makeText(this,"Save Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
