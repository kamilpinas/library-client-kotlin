package com.example.ppsm_budzik_shoutbox.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppsm_budzik_shoutbox.CustomAdapter
import com.example.ppsm_budzik_shoutbox.Message
import com.example.ppsm_budzik_shoutbox.R
import com.example.ppsm_budzik_shoutbox.ui.shoutbox.ShoutboxFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_shoutbox.*

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var editText: EditText

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val button = root.findViewById<Button>(R.id.loginButton)




        editText = root.findViewById<EditText>(R.id.loginInput)
        loadLogin()
        button.setOnClickListener {
            saveLogin()
            val bundle = Bundle()
            bundle.putString("login", editText.text.toString())
            val fragment: Fragment = ShoutboxFragment()
            fragment.arguments = bundle
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, fragment)
                ?.commit()
        }
        return root
    }


    private fun saveLogin(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.saved_login), editText.text.toString())
            commit()
        }
    }
    private fun loadLogin(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.login)
        editText.setText(sharedPref.getString(getString(R.string.saved_login), defaultValue))
    }
}
