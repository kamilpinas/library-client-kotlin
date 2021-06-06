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
    fun getWypozyczeniaKlientowArray(): Call<Array<MyWypozyczenia>?>?

    @GET("library/client")
    fun getClientArray(@Query("login") login: String, @Query("password") password: String): Call<ClientData>

    @POST("library/client/login")
    fun clientLogin(@Body MyLogin: MyLogin): Call<MyLogin>

    @PUT("library/client/update")
    fun updateClient(@Body ClientData: ClientData): Call<ClientData>

    @POST("library/admin/login")
    fun createPostAdmin(@Body MyLogin: MyLogin): Call<MyLogin>

    @POST("library/client/register")
    fun clientRegister(@Body ClientData: ClientData): Call<ClientData>

    @POST("library/publishing_houses/add")
    fun createPublishingHouse(@Body MyWydawnictwa: MyWydawnictwa): Call<MyWydawnictwa>

    @GET("library/publishing_houses/all")
    fun getWydawnictwaArray(): Call<Array<MyWydawnictwa>?>?

    @GET("library/books/category/all")
    fun getCategoryArray(): Call<Array<MyCategory>?>?

    @POST("library/authors/add")
    fun createAuthor(@Body MyAutor: MyAutor): Call<MyAutor>

    @GET("library/authors/all")
    fun getAutorsArray(): Call<Array<MyAutor>?>?

    @POST("library/books/add")
    fun createBook(@Body MyBooks: MyBooks): Call<MyBooks>

    @PUT("library/books/update")
    fun updateBook(@Body ClientData: ClientData): Call<ClientData>

    @DELETE("library/client/delete_account")
    fun deleteClient(@Query("login") login: String, @Query("password") password: String): Call<MyLogin>

    @POST("library/books/rent")
    fun wypozyczKsiazke(@Query("login") login: String, @Query("password") password: String, @Query("book_id") book_id: Long): Call<MyLogin>

    @DELETE("library/books/delete_book")
    fun usunKsiazke(@Query("book_id") book_id: Long): Call<MyBooks>

    @DELETE("library/books/rented/delete_rent")
    fun usunWypozyczenie(@Query("rentalId") rentalId: Long): Call<MyWypozyczenia>
}