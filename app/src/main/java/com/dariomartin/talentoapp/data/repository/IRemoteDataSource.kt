package com.dariomartin.talentoapp.data.repository

import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.domain.model.Character

interface IRemoteDataSource {
    suspend fun getCharacters(): Response<List<Character>>
    suspend fun getCharacter(id: String): Response<Character>
}
