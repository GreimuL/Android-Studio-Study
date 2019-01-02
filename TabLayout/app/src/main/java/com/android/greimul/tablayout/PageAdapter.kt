package com.android.greimul.tablayout

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter

class PageAdapter(fm:FragmentManager,var tabcnt:Int):FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return tabcnt
    }
    override fun getItem(p0: Int): Fragment? {
        when(p0){
            0 -> return Page1()
            1 -> return Page2()
            2 -> return Page3()
        }
        return null
    }
}