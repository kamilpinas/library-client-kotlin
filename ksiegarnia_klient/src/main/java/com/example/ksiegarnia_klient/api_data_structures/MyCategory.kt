package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class MyCategory: Serializable {
    var categoryId: String? = null
    var title: String? = null

    constructor(title: String?) {
        this.title = title
    }

    override fun toString(): String {
        return "$title"
    }
}