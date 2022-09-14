package com.dariomartin.talentoapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.data.repository.IRemoteDataSource
import com.dariomartin.talentoapp.data.toModelCharacter
import com.dariomartin.talentoapp.domain.model.Character
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(private val dataSource: IRemoteDataSource, private val query: String) :
    PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val offset: Int = params.key ?: 0
            val loadSize = params.loadSize

            val response = dataSource.getCharacters(
                limit = loadSize,
                offset = offset,
                query = query
            )

            when (response) {
                is Response.Error -> {
                    LoadResult.Error(Exception(response.message))
                }
                is Response.Success -> {
                    val data = response.data

                    val responseOffset = data?.data?.offset ?: 0
                    val responseLimit = data?.data?.limit ?: 0
                    val responseTotal = data?.data?.total ?: 0

                    val prevKey = null
                    val nextKey =
                        if (responseOffset + responseLimit >= responseTotal) null
                        else responseOffset + loadSize

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