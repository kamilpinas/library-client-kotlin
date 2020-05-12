package com.example.lab9_f

import com.example.ppsm_budzik_shoutbox.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JsonPlaceholderAPI {
    @GET("shoutbox/messages")
    fun getMessageArray(): Call<Array<Message>?>?

    @POST("shoutbox/message")
    fun createPost(@Body Message: Message): Call<Message>
}