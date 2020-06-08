package com.example.ksiegarnia_klient

import java.io.Serializable

class MyLogin : Serializable {
    var login: String? = null
    var password: String? = null
    var isAdmin: Boolean=false

    constructor(login: String?, password: String?, isAdmin: Boolean) {
        this.login = login
        this.password = password
        this.isAdmin=isAdmin
    }
}