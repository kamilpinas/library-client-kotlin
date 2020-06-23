package com.example.ksiegarnia_klient

import java.io.Serializable

/*class MyLogin : Serializable {
    var login: String? = null
    var password: String? = null
}*/

class MyLogin(
    var login: String?,
    var haslo: String?
) : Serializable