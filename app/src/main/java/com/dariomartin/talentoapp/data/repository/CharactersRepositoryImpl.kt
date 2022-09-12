package com.dariomartin.talentoapp.data.repository

import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.repository.ICharactersRepository

class CharactersRepositoryImpl(
    private val remoteDataSource: IRemoteDataSource
) : ICharactersRepository {

    override suspend fun getCharacters(): Response<List<Character>> {
        return remoteDataSource.getCharacters()
    }

    override suspend fun getCharacterDetails(id: String): Response<Character> {
        return remoteDataSource.getCharacter(id)
    }
}