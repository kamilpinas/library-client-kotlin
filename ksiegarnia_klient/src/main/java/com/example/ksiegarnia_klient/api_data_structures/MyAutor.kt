package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class MyAutor : Serializable {
    var authorId: String? = null
    var surname: String? = null
    var name: String? = null
    var nationality: String? = null
    var publicationPeriod: String? = null
    var writingLanguage: String? = null

    constructor(
        surname: String?,
        name: String?,
        nationality: String?,
        publicationPeriod: String?,
        writingLanguage: String?
    ) {
        this.surname = surname
        this.name = name
        this.nationality = nationality
        this.publicationPeriod = publicationPeriod
        this.writingLanguage = writingLanguage
    }

    constructor()

}