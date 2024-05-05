package com.project.gymapp.manager

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs {
    private val TAG = SharedPrefs::class.java.simpleName

    companion object {
        var user_no = "user_no"
        var user_name = "user_name"
        var user_mobile = "use_mobile"
        var user_type = "user_type"

        private val SHARED_PREFS_FILES_NAMES = "MyData"
        private fun getPrefst(context: Context):SharedPreferences{
            return context.getSharedPreferences(SHARED_PREFS_FILES_NAMES,Context.MODE_PRIVATE)
        }
        fun save (context: Context,key:String,value: String){
            getPrefst(context).edit().putString(key,value).apply()
        }
        fun getString (context: Context,key: String):String?{
            return getPrefst(context).getString(key,"")
        }
        fun clearSharedPrefs(context: Context){
            getPrefst(context).edit().clear().apply()
        }
    }
}