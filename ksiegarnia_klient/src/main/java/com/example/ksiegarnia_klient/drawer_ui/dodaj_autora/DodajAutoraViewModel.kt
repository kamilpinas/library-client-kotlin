package com.example.ksiegarnia_klient.drawer_ui.dodaj_autora
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Add author view model
 *
 * @constructor Create empty Add author view model
 */
class DodajAutoraViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}