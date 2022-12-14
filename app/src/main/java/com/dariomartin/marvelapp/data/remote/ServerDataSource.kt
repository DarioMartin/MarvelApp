package com.dariomartin.marvelapp.data.remote

import com.dariomartin.marvelapp.BuildConfig
import com.dariomartin.marvelapp.data.Response
import com.dariomartin.marvelapp.data.remote.model.CharactersResponse
import com.dariomartin.marvelapp.data.repository.IRemoteDataSource
import com.dariomartin.marvelapp.data.toModelCharacter
import com.dariomartin.marvelapp.domain.model.Character
import java.math.BigInteger
import java.security.MessageDigest

class ServerDataSource(private val charactersApi: CharactersApi) : IRemoteDataSource {

    override suspend fun getCharacters(
        offset: Int,
        limit: Int,
        query: String
    ): Response<CharactersResponse> {
        return try {
            val ts = System.currentTimeMillis()
            val hash = getHash(ts)

            val response = charactersApi.getCharacters(
                offset = offset,
                limit = limit,
                apiKey = BuildConfig.PUBLIC_API_KEY,
                ts = ts,
                hash = hash,
                query = query.ifEmpty { null }
            )

            if (response.isSuccessful) {
                response.body()?.let { Response.Success(data = it) }
                    ?: Response.Error(response.message() ?: "Could not load the characters")
            } else {
                Response.Error(response.message() ?: "Could not load the characters")
            }

        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Could not load the characters")
        }
    }

    override suspend fun getCharacter(id: Int): Response<Character> {
        return try {
            val ts = System.currentTimeMillis()
            val hash = getHash(ts)

            val response = charactersApi.getCharacterDetails(
                id = id,
                apiKey = BuildConfig.PUBLIC_API_KEY,
                ts = ts,
                hash = hash
            )

            if (response.isSuccessful) {
                response.body()?.data?.results?.firstOrNull()
                    ?.let { Response.Success(data = it.toModelCharacter()) } ?: Response.Error(
                    response.message() ?: "Could not load the character"
                )
            } else {
                Response.Error(response.message() ?: "Could not load the character")
            }

        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Could not load the character")
        }
    }

}

fun getHash(ts: Long): String {
    val input = "$ts${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}"
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}