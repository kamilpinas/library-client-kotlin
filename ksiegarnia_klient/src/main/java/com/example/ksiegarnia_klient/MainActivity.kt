package com.example.ksiegarnia_klient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

lateinit var navView: NavigationView/// zeby w settings fragment tego uzyc do ustawienia odpowiedniego itemu w drawerze po kliknieciu login

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userLogin: String
    private lateinit var headerView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)
        headerView = navView.getHeaderView(0)



        var drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        var currentUserTextView: TextView = headerView.findViewById(R.id.currentUserTextView)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_ksiegarnia
            ), drawerLayout
        )
        navView.setupWithNavController(navController)
        if (currentUserTextView != null) {
            loadData()
            currentUserTextView.text = "Jesteś zalogowany jako: " + userLogin
        }

        navView.setNavigationItemSelectedListener(this)
        val intent = Intent(this@MainActivity, StartScreenActivity::class.java)
        startActivity(intent)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun openCloseNavigationDrawer(view: View) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_wyloguj -> {
                Log.d("LOL", "L:OPL")
                val intent = Intent(this@MainActivity, StartScreenActivity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        userLogin = sharedPreferences.getString("user_login", "gosc_domyslny")!!
    }
}
