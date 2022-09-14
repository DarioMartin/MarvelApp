package com.dariomartin.marvelapp.domain.usecases

import com.dariomartin.marvelapp.data.Response
import com.dariomartin.marvelapp.domain.model.Character
import com.dariomartin.marvelapp.domain.repository.ICharactersRepository

class GetCharacterDetailsUseCase(private val repository: ICharactersRepository) {

    suspend operator fun invoke(id: Int): Response<Character> {
        return repository.getCharacterDetails(id)
    }

}