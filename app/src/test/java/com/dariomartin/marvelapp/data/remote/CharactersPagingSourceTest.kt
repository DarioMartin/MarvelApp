package com.dariomartin.marvelapp.data.remote

import androidx.paging.PagingSource
import com.dariomartin.marvelapp.createCharacters
import com.dariomartin.marvelapp.data.repository.IRemoteDataSource
import com.dariomartin.marvelapp.domain.model.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class CharactersPagingSourceTest {

    companion object {
        private const val TOTAL_ITEMS = 100
    }

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
    fun `load first 2 items`() = runTest {
        val query = ""
        val pagingSource = CharactersPagingSource(remoteDataSource, query)

        assertThat(
            PagingSource.LoadResult.Page(
                data = listOf(mockCharacters[0], mockCharacters[1]),
                prevKey = null,
                nextKey = 2
            )
        ).isEqualTo(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `load next 2 items`() = runTest {
        val query = ""
        val pagingSource = CharactersPagingSource(remoteDataSource, query)

        assertThat(
            PagingSource.LoadResult.Page(
                data = listOf(mockCharacters[2], mockCharacters[3]),
                prevKey = null,
                nextKey = 4
            )
        ).isEqualTo(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 2,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `load last 2 items`() = runTest {
        val query = ""
        val pagingSource = CharactersPagingSource(remoteDataSource, query)

        assertThat(
            PagingSource.LoadResult.Page(
                data = listOf(mockCharacters[TOTAL_ITEMS - 2], mockCharacters[TOTAL_ITEMS - 1]),
                prevKey = null,
                nextKey = 100
            )
        ).isEqualTo(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = TOTAL_ITEMS - 2,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `ask for 2 last items but getting just 1`() = runTest {
        val query = ""
        val pagingSource = CharactersPagingSource(remoteDataSource, query)

        assertThat(
            PagingSource.LoadResult.Page(
                data = listOf(mockCharacters[TOTAL_ITEMS - 1]),
                prevKey = null,
                nextKey = null
            )
        ).isEqualTo(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = TOTAL_ITEMS - 1,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `ask for first items starting with 2`() = runTest {
        val query = "2"
        val pagingSource = CharactersPagingSource(remoteDataSource, query)

        assertThat(
            PagingSource.LoadResult.Page(
                data = listOf(mockCharacters[1]),
                prevKey = null,
                nextKey = 10
            )
        ).isEqualTo(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `test error query`() = runTest {
        val query = ""
        val pagingSource = CharactersPagingSource(errorRemoteDataSource, query)

        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        ).isInstanceOf(
            PagingSource.LoadResult.Error::class.java
        )
    }

}