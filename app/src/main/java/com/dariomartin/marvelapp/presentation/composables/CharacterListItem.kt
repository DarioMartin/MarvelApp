package com.dariomartin.marvelapp.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dariomartin.marvelapp.presentation.theme.MarvelAppTheme
import com.dariomartin.marvelapp.domain.model.Character
import com.dariomartin.marvelapp.R

@Composable
fun CharacterListItem(
    character: Character,
    onClicked: (Int) -> Unit,
) {
    Card(
        modifier = Modifier.aspectRatio(6 / 8F),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { onClicked(character.id) })
                .fillMaxWidth()
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6 / 5F),
                placeholder = painterResource(R.drawable.marvel_eagle_logo),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 3.dp
            )

            Text(
                modifier = Modifier.padding(6.dp),
                text = character.name,
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Preview
@Composable
fun CharacterListItemPrev() {
    MarvelAppTheme {
        CharacterListItem(
            Character(
                id = 1,
                name = "Spiderman",
                imageUrl = "",
                description = "Character description"
            )
        ) {}
    }
}