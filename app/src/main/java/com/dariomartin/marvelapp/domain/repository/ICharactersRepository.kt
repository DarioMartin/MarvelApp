package com.dariomartin.marvelapp.domain.repository

import androidx.paging.PagingSource
import com.dariomartin.marvelapp.data.Response
import com.dariomartin.marvelapp.domain.model.Character

interface ICharactersRepository {
    fun getCharacters(query: String): PagingSource<Int, Character>
    suspend fun getCharacterDetails(id: Int): Response<Character>
}