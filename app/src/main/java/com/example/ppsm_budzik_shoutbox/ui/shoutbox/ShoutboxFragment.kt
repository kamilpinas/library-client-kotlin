package com.example.ppsm_budzik_shoutbox.ui.shoutbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ppsm_budzik_shoutbox.R

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
        val textView: TextView = root.findViewById(R.id.text_home)
        shoutboxViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
