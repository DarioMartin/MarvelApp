package com.dariomartin.talentoapp.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.presentation.theme.TalentoAppTheme


@Composable
fun CharacterListItem(
    character: Character,
    modifier: Modifier = Modifier,
    onClicked: (Int) -> Unit,
) {
    Card(
        modifier = modifier.height(150.dp),
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
                model = character.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Divider(
                color = Color.Red,
                thickness = 3.dp
            )

            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxHeight(),
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
    TalentoAppTheme {
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