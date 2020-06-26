package com.example.ksiegarnia_klient.api_data_structures

import android.view.View
import android.widget.Button
import java.io.Serializable

class WypozyczeniaKlientow : Serializable {
    var idKsiazki: Integer? = null
    var idKlienta: Integer? = null

    var tytulKsiazki: String? = null
    var dataWypozyczenia: String? = null
    var dataZwrotu: String? = null
    var imieWypozyczajacego: String? = null
    var nazwiskoWypozyczajacego: String? = null
}