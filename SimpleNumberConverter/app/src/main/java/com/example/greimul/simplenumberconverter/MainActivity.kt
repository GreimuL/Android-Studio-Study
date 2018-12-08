package com.example.greimul.simplenumberconverter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.Long.*

class MainActivity : AppCompatActivity() {

    var binck:Boolean = false
    var decck:Boolean = true
    var hexck:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binText.isEnabled = false
        decText.isEnabled = true
        hexText.isEnabled = false
        binBT.setOnClickListener{
            binck = true
            decck = false
            hexck = false
            binText.isEnabled = true
            decText.isEnabled = false
            hexText.isEnabled = false
        }
        decBT.setOnClickListener{
            binck = false
            decck = true
            hexck = false
            binText.isEnabled = false
            decText.isEnabled = true
            hexText.isEnabled = false
        }
        hexBT.setOnClickListener{
            binck = false
            decck = false
            hexck = true
            binText.isEnabled = false
            decText.isEnabled = false
            hexText.isEnabled = true
        }
        binText.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binck==true) {
                    try {
                        var dec: Long = parseLong(if (s.isNullOrEmpty()) 0.toString() else s.toString(), 2)
                        decText.setText(dec.toString())
                        hexText.setText(toHexString(dec))
                    }
                    catch(e:Exception){
                        decText.setText("Invalid Data")
                        hexText.setText("Invalid Data")
                    }
                }
            }
        })
        decText.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(decck==true) {
                    try {
                        var dec: Long = parseLong(if (s.isNullOrEmpty()) 0.toString() else s.toString(), 10)
                        binText.setText(toBinaryString(dec))
                        hexText.setText(toHexString(dec))
                    }
                    catch(e:Exception){
                        binText.setText("Invalid Data")
                        hexText.setText("Invalid Data")
                    }
                }
            }
        })
        hexText.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(hexck==true) {
                    try {
                        var dec: Long = parseLong(if (s.isNullOrEmpty()) 0.toString() else s.toString(), 16)
                        binText.setText(toBinaryString(dec))
                        decText.setText(dec.toString())
                    }
                    catch(e:Exception){
                        binText.setText("Invalid Data")
                        decText.setText("Invalid Data")
                    }
                }
            }
        })
    }
}
