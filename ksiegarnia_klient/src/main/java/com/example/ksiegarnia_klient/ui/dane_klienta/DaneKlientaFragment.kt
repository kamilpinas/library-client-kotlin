package com.example.ksiegarnia_klient.ui.dane_klienta

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProviders

import com.example.ksiegarnia_klient.*


class DaneKlientaFragment : Fragment() {
    private lateinit var daneKlientaViewModel: DaneKlientaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       daneKlientaViewModel =
            ViewModelProviders.of(this).get(DaneKlientaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dane_klienta, container, false)

        return root
    }


}