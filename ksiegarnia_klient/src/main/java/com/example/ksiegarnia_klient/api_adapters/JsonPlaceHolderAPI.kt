package com.example.ksiegarnia_klient.api_adapters

import com.example.ksiegarnia_klient.api_data_structures.*
import retrofit2.Call
import retrofit2.http.*

/**
 * JSON REST API ENDPOINTS
 *
 * @constructor Create empty Json placeholder a p i
 */
interface JsonPlaceholderAPI {

    /**
     * Get books array
     *
     * @return
     */
    @GET("library/books/all")
    fun getBookArray(): Call<Array<MyBooks>?>?

    /**
     * Get client rentals array
     *
     * @param login
     * @param password
     * @return
     */
    @GET("library/books/rented/get_client_rentals")
    fun getWypozyczeniaArray(@Query("login") login: String, @Query("password") password: String): Call<Array<MyWypozyczenia>?>?

    /**
     * Get all clients rentals array
     *
     * @return
     */
    @GET("library/books/rented/all")
    fun getWypozyczeniaKlientowArray(): Call<Array<MyWypozyczenia>?>?

    /**
     * Get client data
     *
     * @param login
     * @param password
     * @return
     */
    @GET("library/client")
    fun getClientArray(@Query("login") login: String, @Query("password") password: String): Call<ClientData>

    /**
     * Client login
     *
     * @param MyLogin
     * @return
     */
    @POST("library/client/login")
    fun clientLogin(@Body MyLogin: MyLogin): Call<MyLogin>

    /**
     * Update client data
     *
     * @param ClientData
     * @return
     */
    @PUT("library/client/update")
    fun updateClient(@Body ClientData: ClientData): Call<ClientData>

    /**
     * Login as admin
     *
     * @param MyLogin
     * @return
     */
    @POST("library/admin/login")
    fun createPostAdmin(@Body MyLogin: MyLogin): Call<MyLogin>

    /**
     * Client register
     *
     * @param ClientData
     * @return
     */
    @POST("library/client/register")
    fun clientRegister(@Body ClientData: ClientData): Call<ClientData>

    /**
     * Create publishing house
     *
     * @param MyWydawnictwa
     * @return
     */
    @POST("library/publishing_houses/add")
    fun createPublishingHouse(@Body MyWydawnictwa: MyWydawnictwa): Call<MyWydawnictwa>

    /**
     * Get publishing houses array
     *
     * @return
     */
    @GET("library/publishing_houses/all")
    fun getPublishingHousesArray(): Call<Array<MyWydawnictwa>?>?

    /**
     * Create author
     *
     * @param MyAutor
     * @return
     */
    @POST("library/authors/add")
    fun createAuthor(@Body MyAutor: MyAutor): Call<MyAutor>

    /**
     * Get all authors array
     *
     * @return
     */
    @GET("library/authors/all")
    fun getAutorsArray(): Call<Array<MyAutor>?>?

    /**
     * Create book
     *
     * @param MyBooks
     * @return
     */
    @POST("library/books/add")
    fun createBook(@Body MyBooks: MyBooks): Call<MyBooks>

    /**
     * Update book
     *
     * @param ClientData
     * @return
     */
    @PUT("library/books/update")
    fun updateBook(@Body ClientData: ClientData): Call<ClientData>

    /**
     * Delete client
     *
     * @param login
     * @param password
     * @return
     */
    @DELETE("library/client/delete_account")
    fun deleteClient(@Query("login") login: String, @Query("password") password: String): Call<MyLogin>

    /**
     * Rent a book by a client
     *
     * @param login
     * @param password
     * @param book_id
     * @return
     */
    @POST("library/books/rent")
    fun rentBook(@Query("login") login: String, @Query("password") password: String, @Query("book_id") book_id: Long): Call<MyLogin>

    /**
     * Delete book
     *
     * @param book_id
     * @return
     */
    @DELETE("library/books/delete_book")
    fun deleteBook(@Query("book_id") book_id: Long): Call<MyBooks>

    /**
     * Delete rent
     *
     * @param rentalId
     * @return
     */
    @DELETE("library/books/rented/delete_rent")
    fun deleteRent(@Query("rentalId") rentalId: Long): Call<MyWypozyczenia>
}