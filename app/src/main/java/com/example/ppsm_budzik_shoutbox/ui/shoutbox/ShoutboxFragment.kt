package com.example.ppsm_budzik_shoutbox.ui.shoutbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppsm_budzik_shoutbox.CustomAdapter
import com.example.ppsm_budzik_shoutbox.Message
import com.example.ppsm_budzik_shoutbox.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shoutbox.*

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


        ////////////////////////////////////////////////////////////
        //getting recyclerview from xml

        val recyclerView : RecyclerView = root.findViewById(R.id.recyclerView)

        //adding a layoutmanager
        //recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView?.layoutManager = LinearLayoutManager(root.context)
        //crating an arraylist to store users using the data class user
        val messages = ArrayList<Message>()
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))
        messages.add(Message("LOGIN JAKIS", "TERESC WRIADOMOSCI KURWA", "2020-05-03T17:11:23.743Z"))

        //creating our adapter
        val adapter = CustomAdapter(messages)

        //now adding the adapter to recyclerview
        recyclerView?.adapter = adapter
        ////////////////////////////////////////////////////////////


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
