package com.example.ksiegarnia_klient.api_data_structures

import java.io.Serializable

class ClientData(
    var surname: String?,
    var name: String?,
    var zipCode: String?,
    var city: String?,
    var street: String?,
    var houseNumber: String?,
    var phoneNumber: String?,
    var login: String?,
    var password: String?
) : Serializable {
    var clientId: String? = null
}