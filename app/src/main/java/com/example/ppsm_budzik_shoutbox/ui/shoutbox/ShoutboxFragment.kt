package com.example.ppsm_budzik_shoutbox.ui.shoutbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ppsm_budzik_shoutbox.R
import kotlinx.android.synthetic.main.activity_main.*

class ShoutboxFragment : Fragment() {

    private lateinit var shoutboxViewModel: ShoutboxViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {
        shoutboxViewModel =
                ViewModelProviders.of(this).get(ShoutboxViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_shoutbox, container, false)
        return root
    }
    fun openCloseNavigationDrawer(view: View) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }
}
