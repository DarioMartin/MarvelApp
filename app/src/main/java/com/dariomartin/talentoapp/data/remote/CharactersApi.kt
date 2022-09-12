package com.dariomartin.talentoapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(@Query("apikey") apiKey: String): Response<CharactersResponse>

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacterDetails(
        @Path("id") id: String,
        @Query("apikey") apiKey: String
    ): Response<CharacterResponse>
}