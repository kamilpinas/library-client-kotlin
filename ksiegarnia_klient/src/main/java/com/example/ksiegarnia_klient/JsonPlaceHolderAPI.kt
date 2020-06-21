package com.example.ksiegarnia_klient

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceholderAPI {

    @GET("ksiegarnia/ksiazki")
    fun getBookArray(): Call<Array<MyBooks>?>?

    @GET("ksiegarnia/klient")//TODO ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    fun getClientArray(): Call<Array<ClientData>?>?

    @POST("ksiegarnia/login")
    fun createPost(@Body MyLogin: MyLogin): Call<MyLogin>

    @POST("ksiegarnia/loginAdmin")
    fun createPostAdmin(@Body MyLogin: MyLogin): Call<MyLogin>

    @POST("ksiegarnia/register")
    fun createPost(@Body ClientData: ClientData): Call<ClientData>

    @PUT("shoutbox/message/{id}")
    fun createPut(
        @Path("id") id: String,
        @Body exampleItem: MyLogin
    ): Call<MyLogin>
}