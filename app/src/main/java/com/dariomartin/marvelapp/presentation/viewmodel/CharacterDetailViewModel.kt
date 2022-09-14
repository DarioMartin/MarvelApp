package com.dariomartin.marvelapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.marvelapp.R
import com.dariomartin.marvelapp.data.Response
import com.dariomartin.marvelapp.domain.model.Character
import com.dariomartin.marvelapp.domain.usecases.GetCharacterDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase) :
    ViewModel() {

    var uiState = mutableStateOf<UIState>(UIState.Loading)

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            when (val response = getCharacterDetailsUseCase(id)) {
                is Response.Error -> {
                    uiState.value = UIState.Error
                }
                is Response.Success -> {
                    uiState.value =
                        response.data?.let { UIState.Content(character = it) } ?: UIState.Error
                }
            }
        }
    }
}

sealed class UIState {
    object Loading : UIState()
    object Error : UIState()
    data class Content(val character: Character) : UIState()
}