package com.example.greimul.databindingstudy

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import android.support.v7.app.ActionBarDrawerToggle
import kotlin.properties.Delegates

class ViewModel():BaseObservable() {
    //Case1
    var text = ObservableField("")

    //Case2
    @get:Bindable
    var testText:String = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.testText)
        }
}