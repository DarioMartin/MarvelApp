package com.dariomartin.talentoapp.data.remote

import androidx.paging.PagingSource
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.data.repository.IRemoteDataSource
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.repository.ICharactersRepository

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