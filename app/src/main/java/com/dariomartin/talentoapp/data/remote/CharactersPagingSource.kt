package com.dariomartin.talentoapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.data.repository.IRemoteDataSource
import com.dariomartin.talentoapp.data.toModelCharacter
import com.dariomartin.talentoapp.domain.model.Character
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(private val dataSource: IRemoteDataSource) :
    PagingSource<Int, Character>() {

    companion object {
        const val LIMIT = 20
    }


    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(LIMIT)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(LIMIT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val offset: Int = params.key ?: 0

            val response = dataSource.getCharacters(
                limit = LIMIT,
                offset = offset
            )

            when (response) {
                is Response.Error -> {
                    LoadResult.Error(Exception(response.message))
                }
                is Response.Success -> {
                    val data = response.data

                    val prevKey = null
                    val nextKey = (data?.data?.offset ?: 0) + LIMIT

                    LoadResult.Page(
                        data = data?.data?.results?.map { it.toModelCharacter() } ?: listOf(),
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}