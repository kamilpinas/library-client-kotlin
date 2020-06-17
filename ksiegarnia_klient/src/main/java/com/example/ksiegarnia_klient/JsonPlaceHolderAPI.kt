package com.example.ksiegarnia_klient

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceholderAPI {
    @GET("ksiegarnia/ksiazki")
    fun getBookArray(): Call<Array<MyBooks>?>?

    @POST("ksiegarnia/login")
    fun createPost(@Body MyLogin: MyLogin): Call<MyLogin>

    @POST("ksiegarnia/register")
    fun createPost(@Body MyRegister: MyRegister): Call<MyRegister>

    @PUT("shoutbox/message/{id}")
    fun createPut(
        @Path("id") id: String,
        @Body exampleItem: MyLogin
    ): Call<MyLogin>
}