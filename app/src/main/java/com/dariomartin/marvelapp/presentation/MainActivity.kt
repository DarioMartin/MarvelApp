package com.dariomartin.marvelapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dariomartin.marvelapp.presentation.composables.CharacterDetailView
import com.dariomartin.marvelapp.presentation.composables.CharactersView
import com.dariomartin.marvelapp.presentation.theme.MarvelAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val CHARACTER_LIST = "character_list"
        private const val CHARACTER_DETAIL = "character_detail"
        private const val CHARACTER_ID = "character_id"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarvelAppTheme {
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
                            "$CHARACTER_DETAIL/{$CHARACTER_ID}",
                            arguments = listOf(navArgument(CHARACTER_ID) { type = NavType.IntType })
                        ) { backStackEntry ->
                            backStackEntry.arguments?.getInt(CHARACTER_ID)?.let {
                                CharacterDetailView(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
