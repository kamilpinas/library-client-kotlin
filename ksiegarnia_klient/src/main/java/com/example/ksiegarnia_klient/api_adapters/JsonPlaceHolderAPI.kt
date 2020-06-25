package com.example.ksiegarnia_klient.api_adapters

import com.example.ksiegarnia_klient.api_data_structures.*
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceholderAPI {

    @GET("ksiegarnia/ksiazki")
    fun getBookArray(): Call<Array<MyBooks>?>?

    @GET("ksiegarnia/klient/wypozyczeniaKlienta")
    fun getWypozyczeniaArray(@Query("login") login: String, @Query("password") password: String): Call<Array<MyWypozyczenia>?>?

    @GET("ksiegarnia/klient/wypozyczeniaKlientow")
    fun getWypozyczeniaKlientowArray(@Query("login") login: String, @Query("password") password: String): Call<Array<WypozyczeniaKlientow>?>?

    @GET("ksiegarnia/klient")
    fun getClientArray(@Query("login") login: String, @Query("password") password: String): Call<Array<ClientData>?>?

    @POST("ksiegarnia/login")
    fun createPost(@Body MyLogin: MyLogin): Call<MyLogin>

    @POST("ksiegarnia/loginAdmin")
    fun createPostAdmin(@Body MyLogin: MyLogin): Call<MyLogin>

    @POST("ksiegarnia/register")
    fun createPost(@Body ClientData: ClientData): Call<ClientData>

    @POST("ksiegarnia/addWydawnictwo")
    fun createPost(@Body MyWydawnictwa: MyWydawnictwa): Call<MyWydawnictwa>

    @GET("ksiegarnia/getWydawnictwa")
    fun getWydawnictwaArray(): Call<Array<MyWydawnictwa>?>?

    @POST("ksiegarnia/addAutor")
    fun createPost(@Body MyAutor: MyAutor): Call<MyAutor>

    @GET("ksiegarnia/getAutorzy")
    fun getAutorsArray(): Call<Array<MyAutor>?>?

    @POST("ksiegarnia/addBook")
    fun createPost(@Body MyBooks: MyBooks): Call<MyBooks>

    @PUT("ksiegarnia/update")
    fun createPut(@Body ClientData: ClientData): Call<ClientData>

    @PUT("shoutbox/message/{id}")
    fun createPut(
        @Path("id") id: String,
        @Body exampleItem: MyLogin
    ): Call<MyLogin>

    @DELETE("ksiegarnia/klient/usunkonto")
    fun deleteClient(@Query("login") login: String, @Query("password") password: String): Call<MyLogin>

    @POST("ksiegarnia/ksiazki/wypozycz")
    fun wypozyczKsiazke(@Query("login") login: String, @Query("password") password: String, @Query("idKsiazki") idKsiazki: Integer): Call<MyLogin>

    @DELETE("ksiegarnia/ksiazki/usunKsiazke")
    fun usunKsiazke(@Query("login") login: String, @Query("password") password: String, @Query("idKsiazki") idKsiazki: Integer): Call<MyLogin>

    @DELETE("ksiegarnia/ksiazki/usunWypozyczenie")
    fun usunWypozyczenie(
        @Query("login") login: String, @Query("password") password: String, @Query(
            "idKsiazki"
        ) idKsiazki: Integer, @Query("idKlienta") idKlienta: Integer
    ): Call<WypozyczeniaKlientow>
}