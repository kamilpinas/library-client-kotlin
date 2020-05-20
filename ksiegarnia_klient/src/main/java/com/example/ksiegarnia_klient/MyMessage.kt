package com.example.ksiegarnia_klient

import java.io.Serializable

class MyMessage : Serializable {
    var content: String? = null
    var login: String? = null
    var date: String? = null
    var id: String? = null //TODO:: ID JEST DO EDYCJI

    constructor(login: String?, content: String?) {//TODO:: DO PUT
        this.login = login
        this.content = content
    }
}