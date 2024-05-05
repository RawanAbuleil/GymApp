package com.project.gymapp.global

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
import java.text.SimpleDateFormat

class MyFunction {
    companion object {
        fun getValue (cursor: Cursor,columnName:String):String{
            var value:String=""
            try {
                val col = cursor.getColumnIndex(columnName)
                value = cursor.getString(col)
            }catch (e:Exception){
                e.printStackTrace()
                Log.d("MyFunction","getValue ${e.printStackTrace()}")
                value = ""
            }
            return value

        }
        @SuppressLint("SimpleDateFormat")
        fun returnSqlDataFormat (date:String):String{
            try {
                if(date.trim().isNotEmpty()){
                    val dataFormat1=SimpleDateFormat("dd/MM/yyyy")
                    val firstDate = dataFormat1.parse(date)
                    val dataFormat2=SimpleDateFormat("yyyy-MM-dd")
                    return dataFormat2.format(firstDate)


                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return ""
        }
        @SuppressLint("SimpleDateFormat")
        fun returnUserDataFormat (date:String):String{
            try {
                if(date.trim().isNotEmpty()){
                    val dataFormat1=SimpleDateFormat("yyyy-MM-dd")
                    val firstDate = dataFormat1.parse(date)
                    val dataFormat2=SimpleDateFormat("dd/MM/yyyy")
                    return dataFormat2.format(firstDate)


                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return ""
        }


    }
}