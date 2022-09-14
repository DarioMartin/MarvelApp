package com.dariomartin.talentoapp.data.remote

import com.dariomartin.talentoapp.data.remote.model.CharacterDetailResponse
import com.dariomartin.talentoapp.data.remote.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
        @Query("nameStartsWith") query: String?,
    ): Response<CharactersResponse>

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacterDetails(
        @Path("id") id: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
    ): Response<CharacterDetailResponse>
}
