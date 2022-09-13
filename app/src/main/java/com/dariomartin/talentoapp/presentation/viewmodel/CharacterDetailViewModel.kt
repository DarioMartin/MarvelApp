package com.dariomartin.talentoapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.usecases.GetCharacterDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase) :
    ViewModel() {

    var character = mutableStateOf<Character?>(null)

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            when (val response = getCharacterDetailsUseCase(id)) {
                is Response.Error -> {

                }
                is Response.Success -> {
                    character.value = response.data
                }
            }
        }
    }
}