package com.example.ksiegarnia_klient

import java.io.Serializable

class MyBooks : Serializable {
    var content: String? = null
    var login: String? = null
    var date: String? = null
    var id: String? = null

    constructor(login: String?, content: String?) {
        this.login = login
        this.content = content
    }
}