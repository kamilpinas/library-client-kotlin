package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

/**
 * Client data dto class
 *
 * @constructor Create empty Client data
 */
class ClientData : Serializable{
    var clientId: Long? = null
    var surname: String? = null
    var name: String? = null
    var zipCode: String? = null
    var city: String? = null
    var street: String? = null
    var houseNumber: String? = null
    var phoneNumber: String? = null
    var login: String? = null
    var password: String? = null

    constructor(
        surname: String?,
        name: String?,
        zipCode: String?,
        city: String?,
        street: String?,
        houseNumber: String?,
        phoneNumber: String?,
        login: String?,
        password: String?
    ) {
        this.surname = surname
        this.name = name
        this.zipCode = zipCode
        this.city = city
        this.street = street
        this.houseNumber = houseNumber
        this.phoneNumber = phoneNumber
        this.login = login
        this.password = password
    }

    constructor(
        clientId: Long?,
        surname: String?,
        name: String?,
        zipCode: String?,
        city: String?,
        street: String?,
        houseNumber: String?,
        phoneNumber: String?,
        login: String?,
        password: String?
    ) {
        this.clientId = clientId
        this.surname = surname
        this.name = name
        this.zipCode = zipCode
        this.city = city
        this.street = street
        this.houseNumber = houseNumber
        this.phoneNumber = phoneNumber
        this.login = login
        this.password = password
    }
}
