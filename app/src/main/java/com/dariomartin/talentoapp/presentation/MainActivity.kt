package com.dariomartin.talentoapp.presentation

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.presentation.composables.CharacterDetailView
import com.dariomartin.talentoapp.presentation.composables.CharactersView
import com.dariomartin.talentoapp.presentation.theme.TalentoAppTheme
import com.dariomartin.talentoapp.presentation.viewmodel.CharacterDetailViewModel
import com.dariomartin.talentoapp.presentation.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val CHARACTER_LIST = "character_list"
        const val CHARACTER_DETAIL = "character_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TalentoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = CHARACTER_LIST) {
                        composable(CHARACTER_LIST) {
                            CharactersView { characterId ->
                                navController.navigate("$CHARACTER_DETAIL/$characterId")
                            }
                        }
                        composable(
                            "$CHARACTER_DETAIL/{userId}",
                            arguments = listOf(navArgument("userId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            backStackEntry.arguments?.getInt("userId")?.let {
                                CharacterDetailView(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
