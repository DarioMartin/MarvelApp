package com.dariomartin.talentoapp.presentation.composables

import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dariomartin.talentoapp.R
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.presentation.viewmodel.CharactersViewModel

@Composable
fun CharactersView(
    viewModel: CharactersViewModel = hiltViewModel(),
    onGoToDetails: (Int) -> Unit
) {

    val characters = viewModel.charactersFlow.collectAsLazyPagingItems()
    val loadingMoreItems = characters.loadState.append is LoadState.Loading

    Column() {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.height(40.dp),
                painter = painterResource(id = R.drawable.marvel_logo),
                contentDescription = stringResource(
                    R.string.cd_marvel_logo
                )
            )
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.searchQuery.value,
            onValueChange = { newValue ->
                viewModel.searchCharacters(newValue)
                characters.refresh()
            })

        Divider(
            color = Color.Red,
            thickness = 3.dp
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            content = {

                val itemCount = characters.itemCount
                var lastInitialLetter: Char? = null

                for (index in 0 until itemCount) {
                    val character = characters.peek(index)
                    val initialLetter = getInitialLetter(character)

                    if (character != null && initialLetter != lastInitialLetter) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = initialLetter.toString(),
                                    style = MaterialTheme.typography.h4
                                )
                                Divider(
                                    color = MaterialTheme.colors.onBackground,
                                    thickness = 1.dp
                                )
                            }
                        }
                    }

                    lastInitialLetter = initialLetter

                    item(key = character?.id) {
                        characters[index]?.let { character ->
                            CharacterListItem(
                                character = character
                            ) { onGoToDetails(character.id) }
                        }
                    }
                }

                if (loadingMoreItems) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        )
    }
}

fun getInitialLetter(character: Character?): Char {
    var firstLetter: Char? = character?.name?.firstOrNull()
    if (firstLetter == null || firstLetter.isDigit()) {
        firstLetter = '#'
    }
    return firstLetter
}
