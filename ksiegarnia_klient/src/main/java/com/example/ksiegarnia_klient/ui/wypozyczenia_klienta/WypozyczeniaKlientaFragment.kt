package com.example.ksiegarnia_klient.ui.wypozyczenia_klienta

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProviders

import com.example.ksiegarnia_klient.*


class WypozyczeniaKlientaFragment : Fragment() {
    private lateinit var wypozyczeniaKlientaViewModel: WypozyczeniaKlientaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       wypozyczeniaKlientaViewModel =
            ViewModelProviders.of(this).get(WypozyczeniaKlientaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_wypozyczenia_klienta, container, false)

        return root
    }


}