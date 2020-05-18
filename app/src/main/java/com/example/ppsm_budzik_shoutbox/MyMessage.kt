package com.example.ppsm_budzik_shoutbox

import java.io.Serializable

class MyMessage : Serializable {
    var content: String? = null
    var login: String? = null
    var date: String? = null
    var id: String? = null

    constructor(login: String?, content: String?) {
        this.login = login
        this.content = content
    }
}