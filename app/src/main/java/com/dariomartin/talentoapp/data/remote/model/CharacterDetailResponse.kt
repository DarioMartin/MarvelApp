package com.dariomartin.talentoapp.data.remote.model

data class CharacterDetailResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)