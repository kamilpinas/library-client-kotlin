package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class MyWydawnictwa: Serializable {
    var id_wydawnictwa: String? = null
    var nazwa: String? = null
    var miasto: String? = null

    constructor(nazwa: String?, miasto: String?) {
        this.nazwa = nazwa
        this.miasto = miasto
    }
}