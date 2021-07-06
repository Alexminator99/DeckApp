package com.everything.deckapp.data.remote

import com.everything.deckapp.data.models.Card
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers(
        "Accept: application/json"
    )
    @GET("{key}")
    suspend fun getJson(
        @Path("key") key: String,
        @Header("secret-key") secretKey: String = "",
    ): Response<Card>
}