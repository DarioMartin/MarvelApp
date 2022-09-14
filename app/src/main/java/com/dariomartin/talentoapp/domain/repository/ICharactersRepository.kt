package com.dariomartin.talentoapp.domain.repository

import androidx.paging.PagingSource
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.domain.model.Character

interface ICharactersRepository {
    fun getCharacters(query: String): PagingSource<Int, Character>
    suspend fun getCharacterDetails(id: Int): Response<Character>
}