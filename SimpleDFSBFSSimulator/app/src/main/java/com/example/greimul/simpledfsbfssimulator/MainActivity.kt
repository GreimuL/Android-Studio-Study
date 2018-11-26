package com.example.greimul.simpledfsbfssimulator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import kotlinx.android.synthetic.main.activity_graph.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    var permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        save.setOnClickListener{
            permissionCheck()
            save()
        }
        load.setOnClickListener{
            permissionCheck()
            load()
        }
        graphSet.setOnClickListener{
            if(graphEdit?.text?.toString()==""){
                Toast.makeText(this,"There is no graph data", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("Data", graphEdit?.text?.toString())
                startActivity(intent)
            }
        }
    }
    fun permissionCheck(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return
        }
        else{
            if(checkCallingOrSelfPermission(permission)==PackageManager.PERMISSION_DENIED){
                requestPermissions(arrayOf(permission),0)
            }
        }
    }
    fun save(){
        var saveData:String? = graphEdit.text?.toString()
        try {
            val path = Environment.getExternalStorageDirectory()
            val directory = File(path,"DFSBFSSimulator")
            directory.mkdirs()
            val fileout = File(directory,"GraphData.txt")
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
    fun load(){
        try {
            val path = Environment.getExternalStorageDirectory()
            val directory = File(path,"DFSBFSSimulator")
            directory.mkdirs()
            val findfile = File(directory,"GraphData.txt")
            if(!findfile.exists()){
                Toast.makeText(this,"Load Failed", Toast.LENGTH_SHORT).show()
                return
            }
            else{
                FileInputStream(findfile).use{
                    graphEdit.setText(it.bufferedReader().use { it.readText() })
                }
                Toast.makeText(this,"Load Success", Toast.LENGTH_SHORT).show()
            }
        }
        catch(e:Exception){
            Toast.makeText(this,"Load Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
