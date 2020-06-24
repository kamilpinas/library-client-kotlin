package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class MyWypozyczenia : Serializable {
    var idKsiazki: String? = null
    var tytulKsiazki: String? = null
    var dataWypozyczenia: String? = null
    var dataZwrotu: String? = null
}