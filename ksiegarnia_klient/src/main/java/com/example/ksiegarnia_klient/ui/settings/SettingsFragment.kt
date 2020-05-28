package com.example.ksiegarnia_klient.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.ksiegarnia_klient.R
import com.example.ksiegarnia_klient.navView
import com.example.ksiegarnia_klient.ui.ksiegarnia.KsiegarniaFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.concurrent.locks.Lock

class SettingsFragment : Fragment() {
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var loginInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val loginAsGuestButton = root.findViewById<Button>(R.id.loginAsGuestButton)
        loginInput = root.findViewById(R.id.loginInput)


        loginAsGuestButton.setOnClickListener {

            navView.setCheckedItem(R.id.nav_shoutbox)


            val bundle = Bundle()
            bundle.putString("login", "gość")

            //bundle.putString("login", loginInput.text.toString()) KOPIA
            val fragment: Fragment = KsiegarniaFragment()
            fragment.arguments = bundle

            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, fragment)
                ?.commit()
        }
        return root
    }
}

