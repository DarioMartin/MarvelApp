package com.dariomartin.marvelapp.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dariomartin.marvelapp.R
import com.dariomartin.marvelapp.domain.getInitialLetter
import com.dariomartin.marvelapp.presentation.theme.Red
import com.dariomartin.marvelapp.presentation.viewmodel.CharactersViewModel

@Composable
fun CharactersView(
    viewModel: CharactersViewModel = hiltViewModel(),
    onGoToDetails: (Int) -> Unit
) {
    val characters = viewModel.charactersFlow.collectAsLazyPagingItems()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MarvelHeader()
        SearchBox()
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 3.dp
        )

        when (val refreshState = characters.loadState.refresh) {
            is LoadState.Error -> Message(
                title = stringResource(R.string.general_error_title),
                body = refreshState.error.localizedMessage
                    ?: stringResource(R.string.general_error_body),
                actionName = stringResource(R.string.retry),
                action = { characters.refresh() }
            )
            LoadState.Loading -> ProgressIndicator()
            is LoadState.NotLoading -> CharacterList { id -> onGoToDetails(id) }
        }
    }
}

@Composable
private fun MarvelHeader() {
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
}

@Composable
private fun SearchBox(viewModel: CharactersViewModel = hiltViewModel()) {

    val characters = viewModel.charactersFlow.collectAsLazyPagingItems()

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Red),
        colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
        placeholder = {
            Text(
                text = stringResource(R.string.character_name),
                color = Color.White.copy(alpha = 0.5F)
            )
        },
        trailingIcon = {
            if (viewModel.searchQuery.value.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.cd_search_icon)
                )
            } else {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = {
                            viewModel.searchCharacters("")
                            characters.refresh()
                        }),
                    imageVector = Icons.Default.Clear,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.cd_clear_icon)
                )
            }
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
        }
    )
}

@Composable
private fun CharacterList(
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
                        LetterHeader(initialLetter)
                    }
                }

                lastInitialLetter = initialLetter

                item(key = character?.id) {
                    characters[index]?.let { character ->
                        CharacterListItem(character = character) { onGoToDetails(character.id) }
                    }
                }
            }

            if (loadingMoreItems) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ProgressIndicator()
                }
            }
        }
    )
}

@Composable
private fun LetterHeader(initialLetter: Char) {
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