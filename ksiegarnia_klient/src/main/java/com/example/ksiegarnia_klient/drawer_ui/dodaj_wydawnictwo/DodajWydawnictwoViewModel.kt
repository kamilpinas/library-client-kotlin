package com.example.ksiegarnia_klient.drawer_ui.dodaj_wydawnictwo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Add publishing house view model
 *
 * @constructor Create empty publishing house view model
 */
class DodajWydawnictwoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}