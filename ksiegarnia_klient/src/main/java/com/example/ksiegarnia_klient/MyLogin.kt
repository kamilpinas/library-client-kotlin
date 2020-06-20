package com.example.ksiegarnia_klient

import java.io.Serializable

class MyLogin : Serializable {
    var login: String? = null
    var password: String? = null

    constructor(login: String?, password: String?) {
        this.login = login
        this.password = password
    }
}