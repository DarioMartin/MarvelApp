package com.dariomartin.marvelapp.data.repository

import com.dariomartin.marvelapp.data.Response
import com.dariomartin.marvelapp.data.remote.model.CharactersResponse
import com.dariomartin.marvelapp.domain.model.Character

interface IRemoteDataSource {
    suspend fun getCharacters(offset: Int, limit: Int, query: String): Response<CharactersResponse>
    suspend fun getCharacter(id: Int): Response<Character>
}
