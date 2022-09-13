package com.dariomartin.talentoapp.data.repository

import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.data.remote.model.CharactersResponse
import com.dariomartin.talentoapp.domain.model.Character

interface IRemoteDataSource {
    suspend fun getCharacters(offset: Int, limit: Int): Response<CharactersResponse>
    suspend fun getCharacter(id: Int): Response<Character>
}
