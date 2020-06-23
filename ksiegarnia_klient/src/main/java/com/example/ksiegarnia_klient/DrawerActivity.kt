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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.ksiegarnia_klient.ui.dane_klienta.DaneKlientaFragment
import com.example.ksiegarnia_klient.ui.ksiegarnia.KsiegarniaFragment
import com.example.ksiegarnia_klient.ui.wypozyczenia_klienta.WypozyczeniaKlientaFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userLogin: String
    private lateinit var headerView: View
    private lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        if (isAdmin == true) {
            fab.visibility = View.VISIBLE
        }

        navView = findViewById(R.id.nav_view)
        headerView = navView.getHeaderView(0)


        var drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        var currentUserTextView: TextView = headerView.findViewById(R.id.currentUserTextView)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_ksiegarnia, R.id.nav_dane_klienta, R.id.nav_wypozyczenia_klienta
            ), drawerLayout
        )
        navView.setupWithNavController(navController)
        if (currentUserTextView != null) {
            loadData()
            if (isAdmin) {
                currentUserTextView.text =
                    "Jesteś zalogowany jako: " + userLogin + ", masz prawa administratora"
            } else {
                currentUserTextView.text = "Jesteś zalogowany jako: " + userLogin
            }
        }
        navView.setNavigationItemSelectedListener(this)
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
                val intent = Intent(this@DrawerActivity, StartScreenActivity::class.java)
                finish()
                startActivity(intent)
            }
            R.id.nav_dane_klienta -> {
                val fragment: Fragment = DaneKlientaFragment()
                val fragmentManager: FragmentManager = supportFragmentManager
                fragmentManager.beginTransaction()
                    ?.replace(
                        R.id.nav_host_fragment,
                        fragment
                    )
                    ?.addToBackStack(null)//TODO:: TE REPLACE NIE ZAMIENIAJA TYLKO DODAJA KOLEIJNY, BO fragmenty sa na sztywno w xml, trzeba frame layout?
                    ?.commit()
            }

            R.id.nav_ksiegarnia -> {
                val fragment: Fragment = KsiegarniaFragment()
                val fragmentManager: FragmentManager = supportFragmentManager
                fragmentManager.beginTransaction()
                    ?.replace(R.id.nav_host_fragment, fragment, "KSIEGARNIA")
                    ?.addToBackStack(null)
                    ?.commit()
            }

            R.id.nav_wypozyczenia_klienta -> {
                val fragment: Fragment = WypozyczeniaKlientaFragment()
                val fragmentManager: FragmentManager = supportFragmentManager
                fragmentManager.beginTransaction()
                    ?.replace(R.id.nav_host_fragment, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        userLogin = sharedPreferences.getString("user_login", "gosc_domyslny")!!
    }

    override fun onBackPressed() {//TODO:: BEZ TEGO PRZENOSI DO BIALEGO EKRANU XD
        val fragmentManager: FragmentManager = supportFragmentManager
        //  var currentFragment = supportFragmentManager.findFragmentByTag("KSIEGARNIA")
        var currentFragment = supportFragmentManager.fragments.last()

        //currentFragment = supportFragmentManager.findFragmentByTag("nazwa")


        if (currentFragment != null) {
            if (currentFragment.tag == "KSIEGARNIA") {
                finish()
                finishAffinity()
                Log.d("DD", "1")
            } else {
                fragmentManager.popBackStack()
            }
        }
    }
}/*
            } else if (currentFragment.tag == "nazwa") {
                Log.d("DD", "12dasdasas")
                currentFragment = supportFragmentManager.fragments.last()
                fragmentManager.popBackStack()
            } else {
                // fragmentManager.popBackStack()
                Log.d("DD", "12")
                val fragment: Fragment = KsiegarniaFragment()

                fragmentManager.beginTransaction()
                    ?.replace(R.id.nav_host_fragment, fragment, "KSIEGARNIA")
                    ?.commit()
            }
        }else{
            Log.d("DD", "NULL")
            finish()
            finishAffinity()
        }
        return
    }*/

