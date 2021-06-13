package com.example.ksiegarnia_klient.drawer_ui.wypozyczenia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Rentals view model
 *
 * @constructor Create empty Rentals view model
 */
class WypozyczeniaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}