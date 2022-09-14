package com.dariomartin.talentoapp.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dariomartin.talentoapp.R
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.presentation.theme.Red
import com.dariomartin.talentoapp.presentation.viewmodel.CharactersViewModel

@Composable
fun CharactersView(
    viewModel: CharactersViewModel = hiltViewModel(),
    onGoToDetails: (Int) -> Unit
) {
    val characters = viewModel.charactersFlow.collectAsLazyPagingItems()

    characters.loadState.append is LoadState.Error
    characters.loadState.refresh is LoadState.Error

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .background(color = Red)
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
            placeholder = {
                Text(text = stringResource(R.string.character_name))
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.cd_search_icon)
                )
            },
            value = viewModel.searchQuery.value,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    characters.refresh()
                }
            ),
            onValueChange = { newValue ->
                viewModel.searchCharacters(newValue)
            })

        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 3.dp
        )

        when (val a = characters.loadState.refresh) {
            is LoadState.Error -> Message(
                title = stringResource(R.string.general_error_title),
                body = a.error.localizedMessage ?: stringResource(R.string.general_error_body),
                actionName = stringResource(R.string.retry),
                action = { characters.refresh() }
            )
            LoadState.Loading -> Loading()
            is LoadState.NotLoading -> CharacterList { id -> onGoToDetails(id) }
        }
    }
}

@Composable
fun CharacterList(
    viewModel: CharactersViewModel = hiltViewModel(),
    onGoToDetails: (Int) -> Unit
) {

    val characters = viewModel.charactersFlow.collectAsLazyPagingItems()

    val loadingMoreItems = characters.loadState.append is LoadState.Loading
    val refreshing = characters.loadState.refresh is LoadState.Loading

    if (!refreshing && characters.itemCount == 0) {
        Message(
            title = stringResource(R.string.empty_list_title),
            body = stringResource(R.string.empty_list_body)
        )
        return
    }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
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
                    Loading()
                }
            }
        }
    )
}

@Composable
fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

fun getInitialLetter(character: Character?): Char {
    var firstLetter: Char? = character?.name?.firstOrNull()
    if (firstLetter == null || firstLetter.isDigit()) {
        firstLetter = '#'
    }
    return firstLetter
}
