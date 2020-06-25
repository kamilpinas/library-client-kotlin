package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class MyAutor : Serializable {
    var id_autora: String? = null
    var nazwisko: String? = null
    var imie: String? = null
    var narodowosc: String? = null
    var okres_tworzenia: String? = null
    var jezyk: String? = null
    constructor(nazwisko: String?, imie: String?, narodowosc: String?, okres_tworzenia: String?, jezyk: String? ){
        this.nazwisko = nazwisko
        this.imie = imie
        this.narodowosc = narodowosc
        this.okres_tworzenia = okres_tworzenia
        this.jezyk = jezyk
    }
}