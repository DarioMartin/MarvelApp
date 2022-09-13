package com.dariomartin.talentoapp.presentation.composables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.dariomartin.talentoapp.presentation.viewmodel.CharactersViewModel

@Composable
fun CharactersView(viewModel: CharactersViewModel = hiltViewModel(), onGoToDetails: (Int) -> Unit) {
    val characters = viewModel.charactersFlow.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            val itemCount = characters.itemCount
            for (index in 0 until itemCount) {
                val note = characters.peek(index)
                item(key = note?.id) {
                    characters[index]?.let { character ->
                        CharacterListItem(character) { onGoToDetails(character.id) }
                    }
                }
            }
        }
    )
}