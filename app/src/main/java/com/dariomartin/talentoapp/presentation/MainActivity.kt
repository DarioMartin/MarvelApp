package com.dariomartin.talentoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.dariomartin.talentoapp.R
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.presentation.theme.TalentoAppTheme
import com.dariomartin.talentoapp.presentation.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalentoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: CharactersViewModel = viewModel()
                    CharactersView()
                }
            }
        }
    }
}

@Composable
fun CharactersView() {
    val viewModel: CharactersViewModel = viewModel()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            itemsIndexed(viewModel.characters) { index, character ->
                CharacterListItem(character)
            }
        }
    )
}

@Composable
fun CharacterListItem(character: Character) {
    Column(
        modifier = Modifier.fillMaxWidth()
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
        CharacterListItem(Character(id = 1, name = "Spiderman", imageUrl = ""))
    }
}