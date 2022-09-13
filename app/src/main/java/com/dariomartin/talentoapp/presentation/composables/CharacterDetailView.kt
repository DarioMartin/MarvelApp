package com.dariomartin.talentoapp.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dariomartin.talentoapp.presentation.viewmodel.CharacterDetailViewModel

@Composable
fun CharacterDetailView(id: Int, viewModel: CharacterDetailViewModel = hiltViewModel()) {
    viewModel.loadCharacter(id)

    val character = viewModel.character.value
    if (character != null) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F),
                model = character.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(
                text = character.name,
                style = MaterialTheme.typography.h5
            )

            Text(
                text = character.description,
                style = MaterialTheme.typography.body2
            )
        }
    }
}