package com.dariomartin.talentoapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dariomartin.talentoapp.MainCoroutineRule
import com.dariomartin.talentoapp.createCharacters
import com.dariomartin.talentoapp.data.remote.FakeCharacterRepository
import com.dariomartin.talentoapp.data.remote.MockRemoteDataSource
import com.dariomartin.talentoapp.data.repository.IRemoteDataSource
import com.dariomartin.talentoapp.domain.model.Character
import com.dariomartin.talentoapp.domain.usecases.GetCharacterDetailsUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {
    companion object {
        private const val TOTAL_ITEMS = 100
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var mockCharacters: List<Character>
    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var errorRemoteDataSource: IRemoteDataSource

    @Before
    fun setup() {
        mockCharacters = createCharacters(TOTAL_ITEMS)
        remoteDataSource = MockRemoteDataSource(success = true, characters = mockCharacters)
        errorRemoteDataSource = MockRemoteDataSource(success = false, characters = mockCharacters)
    }

    @Test
    fun `test success state`() = runTest {
        val repository = FakeCharacterRepository(remoteDataSource)
        viewModel = CharacterDetailViewModel(GetCharacterDetailsUseCase(repository))

        var state = viewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(UIState.Loading::class.java)

        viewModel.loadCharacter(1)

        state = viewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(UIState.Content::class.java)

    }

    @Test
    fun `test error state`() = runTest {
        val repository = FakeCharacterRepository(errorRemoteDataSource)
        viewModel = CharacterDetailViewModel(GetCharacterDetailsUseCase(repository))

        var state = viewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(UIState.Loading::class.java)

        viewModel.loadCharacter(1)

        state = viewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(UIState.Error::class.java)
    }
}