package com.dariomartin.talentoapp.data.repository

import androidx.paging.PagingSource
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.data.remote.CharactersPagingSource
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.repository.ICharactersRepository

class CharactersRepositoryImpl(
    private val remoteDataSource: IRemoteDataSource
) : ICharactersRepository {

    override fun getCharacters(): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource)
    }

    override suspend fun getCharacterDetails(id: Int): Response<Character> {
        return remoteDataSource.getCharacter(id)
    }
}