package com.example.ksiegarnia_klient.drawer_ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.ksiegarnia_klient.R
import com.example.ksiegarnia_klient.activities_ui.StartScreenActivity
import com.example.ksiegarnia_klient.drawer_ui.dane_klienta.DaneKlientaFragment
import com.example.ksiegarnia_klient.drawer_ui.dodaj_ksiazke.DodajKsiazkeFragment
import com.example.ksiegarnia_klient.drawer_ui.dodaj_autora.DodajAutoraFragment
import com.example.ksiegarnia_klient.drawer_ui.dodaj_wydawnictwo.DodajWydawnictwoFragment
import com.example.ksiegarnia_klient.drawer_ui.ksiegarnia.KsiegarniaFragment
import com.example.ksiegarnia_klient.activities_ui.isAdmin
import com.example.ksiegarnia_klient.drawer_ui.wypozyczenia.WypozyczeniaFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Drawer activity - Side menu bar to navigate between screens
 *
 * @constructor Create empty Drawer activity
 */
class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userLogin: String
    private lateinit var headerView: View
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fabDodajAutora: FloatingActionButton = findViewById(R.id.fabDodajAutora)
        val fabDodajWydawnictwo: FloatingActionButton = findViewById(R.id.fabDodajWydawnictwo)
        val fabDodajKsiazke: FloatingActionButton = findViewById(R.id.fabDodajKsiazke)


        val DodajAutoraTextView: TextView = findViewById(R.id.dodajAutoraTextView)
        val DodajKsiazkeTextView: TextView = findViewById(R.id.dodajKsiazkeTextView)
        val DodajWydawnictwoTextView: TextView = findViewById(R.id.dodajWydawnictwoTextView)
        val adminPanelBackground: ConstraintLayout = findViewById(R.id.adminPanelBackground)

        fabDodajAutora.setOnClickListener { view ->
            val fragment: Fragment = DodajAutoraFragment()

            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager?.beginTransaction()
                ?.add(R.id.nav_host_fragment, fragment, "nazwaaa")
                ?.addToBackStack(this.toString())
                ?.commit()
        }

        fabDodajWydawnictwo.setOnClickListener { view ->
            val fragment: Fragment = DodajWydawnictwoFragment()

            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager?.beginTransaction()
                ?.add(R.id.nav_host_fragment, fragment, "nazwaaa")
                ?.addToBackStack(this.toString())
                ?.commit()
        }

        fabDodajKsiazke.setOnClickListener { view ->
            val fragment: Fragment = DodajKsiazkeFragment()

            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager?.beginTransaction()
                ?.add(R.id.nav_host_fragment, fragment, "nazwaaa")
                ?.addToBackStack(this.toString())
                ?.commit()
        }

        navView = findViewById(R.id.nav_view)
        headerView = navView.getHeaderView(0)

        if (isAdmin == true) {
            fabDodajAutora.visibility = View.VISIBLE
            fabDodajWydawnictwo.visibility = View.VISIBLE
            fabDodajKsiazke.visibility = View.VISIBLE

            DodajAutoraTextView.visibility = View.VISIBLE
            DodajKsiazkeTextView.visibility = View.VISIBLE
            DodajWydawnictwoTextView.visibility = View.VISIBLE
            adminPanelBackground.visibility = View.VISIBLE

            val menu: Menu = navView.getMenu();

            menu.findItem(R.id.nav_wypozyczenia_klienta).setTitle("Wypożyczenia klientów");


        }
        var drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        var currentUserTextView: TextView = headerView.findViewById(R.id.currentUserTextView)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_ksiegarnia,
                R.id.nav_dane_klienta,
                R.id.nav_wypozyczenia_klienta
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

    /**
     * Open close navigation drawer
     *
     * @param view
     */
    fun openCloseNavigationDrawer(view: View) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    /**
     * On navigation item selected
     *
     * @param item
     * @return
     */
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
                val fragment: Fragment = WypozyczeniaFragment()
                if (isAdmin) {
                    R.id.nav_wypozyczenia_klienta
                }
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

    /**
     * Load data from device storage
     *
     */
    private fun loadData() {
        val sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        userLogin = sharedPreferences.getString("user_login", "gosc_domyslny")!!
    }

    /**
     * On back pressed
     *
     */
    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        //  var currentFragment = supportFragmentManager.findFragmentByTag("KSIEGARNIA")
        var currentFragment = supportFragmentManager.fragments.last()

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
}