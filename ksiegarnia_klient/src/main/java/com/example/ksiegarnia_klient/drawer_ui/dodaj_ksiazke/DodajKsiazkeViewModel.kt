package com.example.ksiegarnia_klient.drawer_ui.dodaj_ksiazke
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Add book view model
 *
 * @constructor Create empty Add book view model
 */
class DodajKsiazkeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}