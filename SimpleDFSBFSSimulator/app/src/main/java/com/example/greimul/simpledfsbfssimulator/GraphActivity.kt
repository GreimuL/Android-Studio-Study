package com.example.greimul.simpledfsbfssimulator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.style.UpdateAppearance
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.coroutines.coroutineContext

class GraphActivity:AppCompatActivity(){

    var graphString:String? =null
    var chararr:CharArray? = null
    var grapharr = Array(100,{CharArray(100)})
    var width:Int = -1
    var height:Int = -1
    var cnt:Int = 0
    var startX:Int = -1
    var startY:Int = -1
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        var intent:Intent = getIntent()
        graphString = intent.getStringExtra("Data")
        chararr = graphString!!.toCharArray()
        showgraph()
        makematrix()
        dfs.setOnClickListener{
            Toast.makeText(this,"DFS Start $startX $startY",Toast.LENGTH_SHORT).show()
            DFS(startX,startY)
            showgraph()
        }
        bfs.setOnClickListener{
            Toast.makeText(this,"BFS Start $startX $startY",Toast.LENGTH_SHORT).show()
            var queueX: Queue<Int> = ArrayDeque<Int>()
            var queueY: Queue<Int> = ArrayDeque<Int>()
            var dx = arrayListOf<Int>(0,0,-1,1)
            var dy = arrayListOf<Int>(-1,1,0,0)
            queueX.add(startX)
            queueY.add(startY)
            while(queueX.peek()!=null){
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
                showgraph()
            }
        }
        back.setOnClickListener{finish()}
    }
    fun showgraph(){
        graphView.setText(chararr!!.joinToString(""))
    }
    fun DFS(x:Int,y:Int){
        var dx = arrayListOf<Int>(0,0,-1,1)
        var dy = arrayListOf<Int>(-1,1,0,0)
        for(i in 0..3) {
            var nex: Int = x + dx[i]
            var ney: Int = y + dy[i]
            if (nex < 0 || ney < 0 || nex >= width || ney >= height) {
                continue
            }
            if (grapharr[nex][ney] == '0') {
                grapharr[nex][ney] = '1'
                chararr!![ney * (width + 1) + nex] = '1'
                DFS(nex,ney)
            }
        }
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