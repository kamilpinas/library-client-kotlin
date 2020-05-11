package com.example.ppsm_budzik_shoutbox

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ////////////////////////////////////////////////////////////////////////
        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.recyclerView) as? RecyclerView

        //adding a layoutmanager
        val manager = LinearLayoutManager(this)

        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val messages = ArrayList<Message>()
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))

        //creating our adapter
        val adapter = CustomAdapter(messages)

        //now adding the adapter to recyclerview
        recyclerView?.adapter = adapter
        ///////////////////////////////////////////////////////////////////////


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END)
        // supportActionBar!!.hide()
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_shoutbox, R.id.nav_settings
            ), drawerLayout
        )


        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
}
