package com.shanta.keepalive

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun getSavedCredentials(context: Context){
        val sharedPref = context.getSharedPreferences("MainPref" ,Context.MODE_PRIVATE)

        username = sharedPref.getString("username", "")!!
        password = sharedPref.getString("password", "")!!


    }

    fun setCredentials(context: Context, usr: String, pwd: String){
        val sharedPref = context.getSharedPreferences("MainPref" ,Context.MODE_PRIVATE)
        val myEdit = sharedPref.edit()
        myEdit.putString("username", usr)
        myEdit.putString("password", pwd)
        myEdit.apply()
    }

}