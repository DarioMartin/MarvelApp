package com.dariomartin.talentoapp.domain.repository

import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.domain.model.Character

interface ICharactersRepository {
    suspend fun getCharacters(): Response<List<Character>>
    suspend fun getCharacterDetails(id: Int): Response<Character>
}