package com.example.greimul.simpledfsbfssimulator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_graph.*
import java.util.*


class GraphActivity:AppCompatActivity(){

    var graphString:String? =null
    var chararr:CharArray? = null
    var grapharr = Array(100,{CharArray(100)})
    var width:Int = -1
    var height:Int = -1
    var cnt:Int = 0
    var startX:Int = -1
    var startY:Int = -1

    var stackX:Deque<Int> = ArrayDeque<Int>()
    var stackY:Deque<Int> = ArrayDeque<Int>()

    var queueX: Queue<Int> = ArrayDeque<Int>()
    var queueY: Queue<Int> = ArrayDeque<Int>()
    var dx = arrayListOf<Int>(0,0,-1,1)
    var dy = arrayListOf<Int>(-1,1,0,0)

    var mHandler:ViewHandler =ViewHandler()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        var intent:Intent = getIntent()
        graphString = intent.getStringExtra("Data")
        chararr = graphString!!.toCharArray()
        makematrix()
        graphView.setText(chararr!!.joinToString(""))
        dfs.setOnClickListener{
            Toast.makeText(this,"DFS Start $startX $startY",Toast.LENGTH_SHORT).show()
            stackX.push(startX)
            stackY.push(startY)
            var dfsThread = DFS()
            dfsThread.start()
        }
        bfs.setOnClickListener{
            Toast.makeText(this,"BFS Start $startX $startY",Toast.LENGTH_SHORT).show()
            queueX.add(startX)
            queueY.add(startY)
            var bfsThread = BFS()
            bfsThread.start()

        }
        back.setOnClickListener{finish()}
    }
    inner class DFS():Thread(){
        override fun run(){
            while(stackX.peek()!=null) {
                var x:Int = stackX.peek()
                var y:Int = stackY.peek()
                var find:Boolean = false
                mHandler?.sendEmptyMessage(0)
                SystemClock.sleep(50)
                for (i in 0..3) {
                    var nex: Int = x + dx[i]
                    var ney: Int = y + dy[i]
                    if (nex < 0 || ney < 0 || nex >= width || ney >= height) {
                        continue
                    }
                    if (grapharr[nex][ney] == '0') {
                        grapharr[nex][ney] = '1'
                        chararr!![ney * (width + 1) + nex] = '1'
                        find = true
                        stackX.push(nex)
                        stackY.push(ney)
                        break
                    }
                }
                if(!find){
                    stackX.pop()
                    stackY.pop()
                }
            }
            mHandler?.sendEmptyMessage(1)
        }
    }
    inner class BFS():Thread(){
        override fun run(){
            while(queueX.peek()!=null){
                mHandler?.sendEmptyMessage(0)
                SystemClock.sleep(50)
                var nowx:Int = queueX.peek()
                var nowy:Int = queueY.peek()
                queueX.remove()
                queueY.remove()
                for(i in 0..3) {
                    var nex: Int = nowx + dx[i]
                    var ney: Int = nowy + dy[i]
                    if (nex < 0 || ney < 0 || nex >= width || ney >= height) {
                        continue
                    }
                    if (grapharr[nex][ney] == '0') {
                        queueX.add(nex)
                        queueY.add(ney)
                        grapharr[nex][ney] = '1'
                        chararr!![ney * (width + 1) + nex] = '1'
                    }
                }
            }
            mHandler.sendEmptyMessage(1)
        }
    }
    inner class ViewHandler:Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if(msg?.what == 0) {
                graphView.setText(chararr!!.joinToString(""))
            }
            else{
                graphView.setText(chararr!!.joinToString(""))
                showFinishToast()
            }
        }
    }
    fun showFinishToast(){
        Toast.makeText(this,"Finish",Toast.LENGTH_SHORT).show()
    }
    fun makematrix(){
        for(i in 0..(graphString!!.length-1)){
            if(chararr!![i]=='\n'&&width==-1){
                width = i
            }
            cnt++
        }
        height = cnt/width
        var row:Int = 0
        var col:Int = 0
        for(i in 0..(graphString!!.length-1)){
            if(chararr!![i] == '\n'){
                row++
                col = 0
                continue
            }
            if(chararr!![i]== '2'){
                if(startX != -1){
                    Toast.makeText(this,"Start point is already Existed",Toast.LENGTH_SHORT).show()
                    finish()
                }
                startX = col
                startY = row
            }
            grapharr[col][row] = chararr!![i]
            col++
        }
        if(startX==-1){
            Toast.makeText(this,"There isn't Start point",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}