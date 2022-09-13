package com.dariomartin.talentoapp.domain.usecases

import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.repository.ICharactersRepository

class GetCharacterDetailsUseCase(private val repository: ICharactersRepository) {

    suspend operator fun invoke(id: Int): Response<Character> {
        return repository.getCharacterDetails(id)
    }

}