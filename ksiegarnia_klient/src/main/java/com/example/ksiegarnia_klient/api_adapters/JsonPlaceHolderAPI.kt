package com.example.ksiegarnia_klient.api_adapters

import com.example.ksiegarnia_klient.api_data_structures.*
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceholderAPI {

    @GET("library/books/all")
    fun getBookArray(): Call<Array<MyBooks>?>?

    @GET("library/books/rented/get_client_rentals")
    fun getWypozyczeniaArray(@Query("login") login: String, @Query("password") password: String): Call<Array<MyWypozyczenia>?>?

    @GET("library/books/rented/all")
    fun getWypozyczeniaKlientowArray(@Query("login") login: String, @Query("password") password: String): Call<Array<WypozyczeniaKlientow>?>?

    @GET("library/client")
    fun getClientArray(@Query("login") login: String, @Query("password") password: String): Call<ClientData>

    @POST("library/client/login")
    fun createPost(@Body MyLogin: MyLogin): Call<MyLogin>

    @POST("library/admin/login")
    fun createPostAdmin(@Body MyLogin: MyLogin): Call<MyLogin>

    @POST("library/client/register")
    fun createPost(@Body ClientData: ClientData): Call<ClientData>

    @POST("library/publishing_houses/add")
    fun createPost(@Body MyWydawnictwa: MyWydawnictwa): Call<MyWydawnictwa>

    @GET("library/publishing_houses/all")
    fun getWydawnictwaArray(): Call<Array<MyWydawnictwa>?>?

    @POST("library/authors/add")
    fun createPost(@Body MyAutor: MyAutor): Call<MyAutor>

    @GET("library/authors/all")
    fun getAutorsArray(): Call<Array<MyAutor>?>?

    @POST("library/books/add")
    fun createPost(@Body MyBooks: MyBooks): Call<MyBooks>

    @PUT("library/books/update")
    fun createPut(@Body ClientData: ClientData): Call<ClientData>

    @DELETE("library/client/delete_account")
    fun deleteClient(@Query("login") login: String, @Query("password") password: String): Call<MyLogin>

    @POST("library/books/rent")
    fun wypozyczKsiazke(@Query("login") login: String, @Query("password") password: String, @Query("book_id") book_id: Long): Call<MyLogin>

    @DELETE("library/books/delete_book")
    fun usunKsiazke(@Query("login") login: String, @Query("password") password: String, @Query("idKsiazki") idKsiazki: Long): Call<MyLogin>

    @DELETE("library/ksiazki/usunWypozyczenie")
    fun usunWypozyczenie(
        @Query("login") login: String, @Query("password") password: String, @Query(
            "idKsiazki"
        ) idKsiazki: Integer, @Query("idKlienta") idKlienta: Integer
    ): Call<WypozyczeniaKlientow>
}