package com.dariomartin.talentoapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.usecases.CharactersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val useCases: CharactersUseCases) :
    ViewModel() {

    var characters = mutableStateListOf<Character>()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            val response = useCases.getCharactersUseCase()
            characters.clear()
            when (response) {
                is Response.Error -> {

                }
                is Response.Success -> {
                    characters.addAll(response.data ?: emptyList())
                }
            }
        }
    }
}