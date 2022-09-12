package com.dariomartin.talentoapp.data.remote

import com.dariomartin.talentoapp.domain.model.Character

data class CharactersResponse(val code: Int, val status: String, val data: ResponseData)

data class ResponseData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterResponse>
)