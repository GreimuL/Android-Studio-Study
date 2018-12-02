package com.example.greimul.simpletimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var startCheck:Boolean = false
    var finishCheck:Boolean = false
    var min:Int = 0
    var sec:Int = 0
    var msec:Int = 0
    var mHandler:ViewHandler = ViewHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        curTime.text = "${min}    ${sec}    ${msec}"
        lapView.setText("")

        startBt.setOnClickListener{
            if(finishCheck== false){
                finishCheck = true
                var timeThread = TimerThread()
                timeThread.start()
            }
            if(startCheck==false) {
                startCheck = true
                startBt.text = "STOP"
            }
            else{
                startCheck = false
                startBt.text = "START"
            }
        }
        lapBt.setOnClickListener{
            lapView.setText("${lapView.text}\n${min}    ${sec}    ${msec}")
        }
        resetBt.setOnClickListener{
            finishCheck = false
            startCheck = false
            min = 0
            sec = 0
            msec = 0
            lapView.setText("")
            curTime.text = "${min}    ${sec}    ${msec}"
            startBt.text = "START"
        }

    }
    inner class TimerThread:Thread(){
        override fun run(){
            while(finishCheck) {
                while (startCheck) {
                    SystemClock.sleep(10)
                    msec++
                    if(msec>=100){
                        msec = 0
                        sec++
                    }
                    if(sec>=60){
                        sec = 0
                        min++
                    }
                    var message:Message = Message()
                    message.what =0
                    message.arg1=msec
                    message.arg2=sec
                    message.obj = min
                    mHandler?.sendMessage(message)
                }
            }
        }
    }
    inner class ViewHandler:Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            curTime.text = "${msg?.obj}    ${msg?.arg2}    ${msg?.arg1}"
        }
    }

}
