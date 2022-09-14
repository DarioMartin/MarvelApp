package com.dariomartin.talentoapp.presentation.composables

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dariomartin.talentoapp.R
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.model.Resource
import com.dariomartin.talentoapp.presentation.viewmodel.CharacterDetailViewModel
import com.dariomartin.talentoapp.presentation.viewmodel.UIState

@Composable
fun CharacterDetailView(id: Int, viewModel: CharacterDetailViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.loadCharacter(id)
    }
    val uiState = viewModel.uiState.value
    val configuration = LocalConfiguration.current
    val imageUrl = if (uiState is UIState.Content) uiState.character.imageUrl else null

    if (configuration.orientation == ORIENTATION_LANDSCAPE) {
        Row {
            DetailsImage(imageUrl)
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(5.dp),
                color = MaterialTheme.colors.primary,
            )
            CharacterDetailsBody(uiState = uiState) {
                viewModel.loadCharacter(id)
            }
        }

    } else {
        Column {
            DetailsImage(imageUrl)
            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 5.dp
            )
            CharacterDetailsBody(uiState = uiState) {
                viewModel.loadCharacter(id)
            }
        }
    }
}

@Composable
private fun DetailsImage(imageUrl: String?) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(6 / 5F),
        placeholder = painterResource(R.drawable.marvel_eagle_logo),
        error = painterResource(R.drawable.marvel_eagle_logo),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun CharacterDetailsBody(uiState: UIState, retry: () -> Unit) {
    when (uiState) {
        is UIState.Content -> Content(uiState.character)
        UIState.Error -> Message(
            title = stringResource(id = R.string.character_error_title),
            body = stringResource(id = R.string.character_error_body),
            actionName = stringResource(id = R.string.retry),
            action = { retry() }
        )
        UIState.Loading -> ProgressIndicator()
    }
}

@Composable
fun Content(character: Character) {
    LazyColumn(contentPadding = PaddingValues(18.dp)) {
        item {
            Text(
                text = character.name,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Medium
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            Text(
                text = character.description.ifEmpty { stringResource(R.string.no_description_found) },
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Light
            )
        }

        item { Collection(name = stringResource(R.string.comics), values = character.comics) }
        item { Collection(name = stringResource(R.string.series), values = character.series) }
        item { Collection(name = stringResource(R.string.events), values = character.events) }

    }
}

@Composable
private fun Collection(name: String, values: List<Resource>) {
    if (values.isEmpty()) return

    var expanded by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(36.dp))

    CollectionHeader(name = name, expanded = expanded) { expanded = it }

    if (expanded) {
        values.forEach {
            CollectionItem(name = it.name) {}
        }
    }
}

@Composable
private fun CollectionHeader(name: String, expanded: Boolean, onExpandedChange: (Boolean) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = name,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1F)
            )

            Icon(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable { onExpandedChange(!expanded) },
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        Divider(
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5F),
            thickness = 1.dp
        )
    }
}

@Composable
private fun CollectionItem(name: String, onClick: () -> Unit) {
    TextButton(modifier = Modifier.fillMaxWidth(), onClick = { onClick() }) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start
        )
    }
}