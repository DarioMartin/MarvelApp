package com.dariomartin.talentoapp.presentation.composables

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.presentation.viewmodel.CharacterDetailViewModel

@Composable
fun CharacterDetailView(id: Int, viewModel: CharacterDetailViewModel = hiltViewModel()) {
    viewModel.loadCharacter(id)

    val character = viewModel.character.value
    val configuration = LocalConfiguration.current

    if (character != null) {

        if (configuration.orientation == ORIENTATION_LANDSCAPE) {
            Row {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(5 / 6F),
                    model = character.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Divider(
                    modifier = Modifier.fillMaxHeight().width(5.dp),
                    color = Color.Red
                )

                CharacterDetailsBody(character = character)
            }

        } else {
            Column {
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
                    thickness = 5.dp
                )

                CharacterDetailsBody(character = character)
            }
        }
    }
}

@Composable
fun CharacterDetailsBody(character: Character) {
    LazyColumn(contentPadding = PaddingValues(18.dp)) {
        item {
            Text(
                text = character.name,
                style = MaterialTheme.typography.h5
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Text(
                text = character.description.ifEmpty { "No description found" },
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Light
            )
        }

        item {
            Collection(name = "Comics", values = character.comics)
        }

        item {
            Collection(name = "Series", values = character.series)
        }

        item {
            Collection(name = "Events", values = character.events)
        }

    }
}

@Composable
fun Collection(name: String, values: List<Pair<String, String>>) {
    if (values.isEmpty()) return

    var expanded by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(36.dp))

    CollectionHeader(name = name, expanded = expanded) { expanded = it }

    if (expanded) {
        values.forEach {
            CollectionItem(name = it.first) {

            }
        }
    }
}

@Composable
fun CollectionHeader(name: String, expanded: Boolean, onExpandedChange: (Boolean) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onExpandedChange(!expanded) }) {

        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = name,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1F)
            )

            Icon(
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
fun CollectionItem(name: String, onClick: () -> Unit) {
    TextButton(onClick = { onClick() }) {
        Text(
            text = name,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Light
        )
    }
}

//Configuration.ORIENTATION_LANDSCAPE