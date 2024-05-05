package com.project.gymapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import com.project.gymapp.activity.HomeActivity
import com.project.gymapp.activity.LoginActivity
import com.project.gymapp.databinding.ActivityMainBinding
import com.project.gymapp.global.DB
import com.project.gymapp.manager.SessionManager

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val splash_delay: Long = 3000
    var db: DB? = null
    var session: SessionManager? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DB(this)
        session = SessionManager(this)
        insertAdminData()
        mDelayHandler = Handler()
        mDelayHandler?.postDelayed(mRunnable, splash_delay)
    }

    private val mRunnable: Runnable = Runnable {
        if(session?.isLoggedIn==true){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun insertAdminData() {
        try {
            val sqlCheck = "SELECT * FROM ADMIN"
            db?.fireQuery(sqlCheck)?.use {
                if (it.count > 0) {
                    Log.d("SplashActivity", "data available")
                } else {
                    val sqlQuery =
                        "INSERT OR REPLACE INTO ADMIN(ID,USER_NAME,PASSWORD,MOBILE)VALUES('1','Admin','123456','888888')"
                    db?.executeQuery(sqlQuery)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDelayHandler?.removeCallbacks(mRunnable)
    }
}