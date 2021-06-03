package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class MyBooks : Serializable {
    var bookId: Long? = null
    var title: String? = null
    var genre: String? = null
    var bookLanguage: String? = null
    var publicationDate: String? = null
    var availability: Boolean
    var description: String? = null

    var publishingHouse: MyWydawnictwa
    var author: MyAutor


    constructor(
        title: String?,
        genre: String?,
        bookLanguage: String?,
        publicationDate: String?,
        availability: Boolean,
        description: String?,
        publishingHouse: MyWydawnictwa,
        author: MyAutor
    ) {
        this.title = title
        this.genre = genre
        this.bookLanguage = bookLanguage
        this.publicationDate = publicationDate
        this.availability = availability
        this.description = description
        this.publishingHouse = publishingHouse
        this.author = author
    }
}