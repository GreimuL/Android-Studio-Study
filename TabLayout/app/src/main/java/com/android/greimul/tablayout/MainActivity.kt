package com.android.greimul.tablayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tablayout.addTab(tablayout.newTab().setText("page1"))
        tablayout.addTab(tablayout.newTab().setText("page2"))
        tablayout.addTab(tablayout.newTab().setText("page3"))
        val adapter = PageAdapter(supportFragmentManager,tablayout.tabCount)
        viewpager.adapter = adapter
        viewpager.addOnPageChangeListener(android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
            override fun onTabSelected(p0: TabLayout.Tab) {
                viewpager.currentItem = p0.position
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
        })
    }
}
