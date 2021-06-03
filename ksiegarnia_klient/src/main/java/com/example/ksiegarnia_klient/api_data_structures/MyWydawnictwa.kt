package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class MyWydawnictwa: Serializable {
    var publishingHouseId: String? = null
    var name: String? = null
    var city: String? = null

    constructor(nazwa: String?, miasto: String?) {
        this.name = nazwa
        this.city = miasto
    }
}