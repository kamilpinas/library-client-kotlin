package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

/**
 * Login dto class
 *
 * @constructor Create empty My login
 */
class MyLogin : Serializable {
    var login: String? = null
    var password: String? = null

    constructor(login: String?, password: String?) {
        this.login = login
        this.password = password
    }
}