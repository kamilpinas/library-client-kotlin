package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

/**
 *  Rentals dto class
 *
 * @constructor Create empty My wypozyczenia
 */
class MyWypozyczenia : Serializable {
    var rentalId: Long? = null
    var book: MyBooks
    var client: ClientData
    var rentalDate: String? = null
    var returnDate: String? = null

    constructor(book: MyBooks, client: ClientData, rentalDate: String?, returnDate: String?) {
        this.book = book
        this.client = client
        this.rentalDate = rentalDate
        this.returnDate = returnDate
    }
}