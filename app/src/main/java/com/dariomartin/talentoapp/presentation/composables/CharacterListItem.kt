package com.dariomartin.talentoapp.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.presentation.theme.TalentoAppTheme


@Composable
fun CharacterListItem(character: Character, onClicked: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClicked(character.id) })
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F),
            model = character.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Divider(
            color = Color.Red,
            thickness = 1.dp
        )

        Text(
            text = character.name,
            style = MaterialTheme.typography.h5
        )
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