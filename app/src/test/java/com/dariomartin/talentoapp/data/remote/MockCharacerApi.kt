package com.dariomartin.talentoapp.data.remote

import com.dariomartin.talentoapp.createCharactersResponse
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.data.remote.model.CharactersResponse
import com.dariomartin.talentoapp.data.repository.IRemoteDataSource
import com.dariomartin.talentoapp.domain.model.Character

class MockRemoteDataSource(private val success: Boolean, private val characters: List<Character>) :
    IRemoteDataSource {

    override suspend fun getCharacters(
        offset: Int,
        limit: Int,
        query: String
    ): Response<CharactersResponse> {
        return if (success) Response.Success(
            data = createCharactersResponse(
                characters = characters,
                offset = offset,
                limit = limit,
                query = query
            )
        ) else Response.Error("")
    }

    override suspend fun getCharacter(id: Int): Response<Character> {
        return Response.Error("")
    }

}
