package com.dariomartin.marvelapp.domain.usecases

import androidx.paging.PagingSource
import com.dariomartin.marvelapp.domain.model.Character
import com.dariomartin.marvelapp.domain.repository.ICharactersRepository

class GetCharactersUseCase(private val repository: ICharactersRepository) {

    operator fun invoke(query: String): PagingSource<Int, Character> {
        return repository.getCharacters(query)
    }

}