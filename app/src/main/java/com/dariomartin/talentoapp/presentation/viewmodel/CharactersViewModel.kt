package com.dariomartin.talentoapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dariomartin.talentoapp.domain.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {
    var searchQuery = mutableStateOf("")

    val charactersFlow = Pager(PagingConfig(pageSize = 20)) {
        getCharactersUseCase(searchQuery.value)
    }.flow.cachedIn(viewModelScope)

    fun searchCharacters(query: String) {
        searchQuery.value = query
    }

}