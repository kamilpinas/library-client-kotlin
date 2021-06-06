package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class MyBooks : Serializable {
    var bookId: Long? = null
    var title: String? = null
    var bookLanguage: String? = null
    var publicationDate: String? = null
    var availability: Boolean
    var description: String? = null

    var publishingHouse: MyWydawnictwa
    var category: MyCategory
    var author: MyAutor


    constructor(
        title: String?,
        bookLanguage: String?,
        publicationDate: String?,
        availability: Boolean,
        description: String?,
        publishingHouse: MyWydawnictwa,
        author: MyAutor,
        category:MyCategory
    ) {
        this.title = title
        this.bookLanguage = bookLanguage
        this.publicationDate = publicationDate
        this.availability = availability
        this.description = description
        this.publishingHouse = publishingHouse
        this.author = author
        this.category=category
    }
}