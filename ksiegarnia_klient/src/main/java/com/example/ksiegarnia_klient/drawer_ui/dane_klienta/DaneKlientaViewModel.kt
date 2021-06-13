package com.example.ksiegarnia_klient.drawer_ui.dane_klienta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Client Data view model
 *
 * @constructor Create empty Client Data view model
 */
class DaneKlientaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}