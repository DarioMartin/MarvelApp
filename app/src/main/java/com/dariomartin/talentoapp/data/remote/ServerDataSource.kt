package com.dariomartin.talentoapp.data.remote

import com.dariomartin.talentoapp.BuildConfig
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.data.repository.IRemoteDataSource
import com.dariomartin.talentoapp.domain.model.Character
import java.lang.Exception

class ServerDataSource(private val charactersApi: CharactersApi) : IRemoteDataSource {

    override suspend fun getCharacters(): Response<List<Character>> {
        return try {
            val response = charactersApi.getCharacters(BuildConfig.API_KEY)
            Response.Success(data = listOf())
        } catch (e: Exception) {
            Response.Error("Could not load the characters")
        }
    }

    override suspend fun getCharacter(id: String): Response<Character> {
        return try {
            val response = charactersApi.getCharacterDetails(id, BuildConfig.API_KEY)
            Response.Error("Could not load the character")
        } catch (e: Exception) {
            Response.Error("Could not load the character")
        }
    }
}