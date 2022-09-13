package com.dariomartin.talentoapp.domain.usecases

import androidx.paging.PagingSource
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.repository.ICharactersRepository

class GetCharactersUseCase(private val repository: ICharactersRepository) {

    operator fun invoke(): PagingSource<Int, Character> {
        return repository.getCharacters()
    }

}