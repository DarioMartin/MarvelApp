package com.dariomartin.talentoapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(): Response<CharactersResponse>

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacterDetails(@Path("id") id: String): Response<CharacterResponse>
}