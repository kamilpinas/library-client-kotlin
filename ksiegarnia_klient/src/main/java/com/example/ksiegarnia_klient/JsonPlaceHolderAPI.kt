package com.example.ksiegarnia_klient

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceholderAPI {
    @GET("ksiegarnia/ksiazki")
    fun getBookArray(): Call<Array<MyBooks>?>?

    @POST("shoutbox/message")
    fun createPost(@Body MyMessage: MyMessage): Call<MyMessage>

    @PUT("shoutbox/message/{id}")
    fun createPut(
        @Path("id") id: String,
        @Body exampleItem: MyMessage
    ): Call<MyMessage>
}