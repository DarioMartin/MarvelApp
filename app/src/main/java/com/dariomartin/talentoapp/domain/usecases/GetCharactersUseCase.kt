package com.dariomartin.talentoapp.domain.usecases

import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.repository.ICharactersRepository

class GetCharactersUseCase(private val repository: ICharactersRepository) {

    suspend operator fun invoke(): Response<List<Character>> {
        return repository.getCharacters()
    }

}