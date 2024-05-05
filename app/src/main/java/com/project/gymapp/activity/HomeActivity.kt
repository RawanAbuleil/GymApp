package com.project.gymapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.project.gymapp.R
import com.project.gymapp.databinding.ActivityHomeBinding
import com.project.gymapp.databinding.ActivityLoginBinding
import com.project.gymapp.fragment.FragmentAddMember
import com.project.gymapp.fragment.FragmentAddUpdateFee
import com.project.gymapp.fragment.FragmentAllMember
import com.project.gymapp.fragment.FragmentChangePassword
import com.project.gymapp.fragment.FragmentFeePending
import com.project.gymapp.global.DB
import com.project.gymapp.manager.SessionManager

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG = "HomeActivity"
    var session: SessionManager? = null
    var db: DB? = null
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)
        session = SessionManager(this)
        setSupportActionBar(binding.homeInclude.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener(this)
        drawer = binding.drawerLayout
        toggle = ActionBarDrawerToggle(
            this, drawer, binding.homeInclude.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val fragment = FragmentAllMember()
        loadFragment(fragment)

    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        if(item.itemId==R.id.logOutMenu){
            logOut()
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home ->{
                val fragment = FragmentAllMember()
                loadFragment(fragment)
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START)
                }
            }
            R.id.nav_add ->{
                val fragment = FragmentAddMember()
                loadFragment(fragment)
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START)
                }
            }
            R.id.nav_nav_fee_pending ->{
                val fragment = FragmentFeePending()
                loadFragment(fragment)
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START)
                }
            }
            R.id.nav_update_fee ->{
                val fragment = FragmentAddUpdateFee()
                loadFragment(fragment)
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START)
                }
            }
            R.id.nav_change_password ->{
                val fragment = FragmentChangePassword()
                loadFragment(fragment)
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START)
                }
            }
            R.id.nav_log_out ->{
                logOut()
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START)
                }
            }

        }
        return true

    }
    private fun logOut(){
        session ?.setLogin(false)
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }
    private fun loadFragment(fragment: Fragment){
        var fragmentManager: FragmentManager ?=null
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_container,fragment,"Home").commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        try {
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_main,menu)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return super.onCreateOptionsMenu(menu)
    }
}