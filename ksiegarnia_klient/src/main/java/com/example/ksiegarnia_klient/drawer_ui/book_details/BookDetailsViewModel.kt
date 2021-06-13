package com.example.ksiegarnia_klient.drawer_ui.ksiegarnia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Book details view model
 *
 * @constructor Create empty Book details view model
 */
class BookDetailsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}