package com.example.ksiegarnia_klient

import java.io.Serializable

class MyBooks : Serializable {
    var idKsiazki: String? = null
    var tytul: String? = null
    var autor: String? = null
    var wydawnictwo: String? = null
    var temat: String? = null
    var jezykKsiazki: String? = null
    var rokWydania: String? = null
    var dostepnosc: String? = null
    var opis: String? = null

    /*
    constructor(login: String?, content: String?) {TODO:: DO PUT
        this.login = login
        this.content = content
    }*/
}