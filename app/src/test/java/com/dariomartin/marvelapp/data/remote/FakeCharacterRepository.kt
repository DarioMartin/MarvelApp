package com.dariomartin.marvelapp.data.remote

import androidx.paging.PagingSource
import com.dariomartin.marvelapp.data.Response
import com.dariomartin.marvelapp.data.repository.IRemoteDataSource
import com.dariomartin.marvelapp.domain.model.Character
import com.dariomartin.marvelapp.domain.repository.ICharactersRepository

class FakeCharacterRepository(
    private val remoteDataSource: IRemoteDataSource
) : ICharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource, query)
    }

    override suspend fun getCharacterDetails(id: Int): Response<Character> {
        return remoteDataSource.getCharacter(id)
    }

}