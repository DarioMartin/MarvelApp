package com.dariomartin.talentoapp.data.remote

import com.dariomartin.talentoapp.BuildConfig
import com.dariomartin.talentoapp.data.Response
import com.dariomartin.talentoapp.data.repository.IRemoteDataSource
import com.dariomartin.talentoapp.data.toModelCharacter
import com.dariomartin.talentoapp.domain.model.Character
import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest

class ServerDataSource(private val charactersApi: CharactersApi) : IRemoteDataSource {

    override suspend fun getCharacters(): Response<List<Character>> {
        return try {
            val ts = System.currentTimeMillis()
            val hash = getHash(ts)

            val response = charactersApi.getCharacters(
                apiKey = BuildConfig.PUBLIC_API_KEY,
                ts = ts,
                hash = hash
            )
            Response.Success(data = response.body()?.data?.results?.map { it.toModelCharacter() }
                ?: emptyList())
        } catch (e: Exception) {
            Response.Error("Could not load the characters")
        }
    }

    override suspend fun getCharacter(id: String): Response<Character> {
        return try {
            val ts = System.currentTimeMillis()
            val hash = getHash(ts)

            val response = charactersApi.getCharacterDetails(
                id = id,
                apiKey = BuildConfig.PUBLIC_API_KEY,
                ts = ts,
                hash = hash
            )
            Response.Error("Could not load the character")
        } catch (e: Exception) {
            Response.Error("Could not load the character")
        }
    }

    private fun getHash(ts: Long): String {
        val input = "$ts${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}