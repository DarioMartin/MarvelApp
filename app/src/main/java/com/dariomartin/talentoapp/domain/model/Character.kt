package com.dariomartin.talentoapp.domain.model

data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val comics: List<Pair<String, String>> = emptyList(),
    val series: List<Pair<String, String>> = emptyList(),
    val events: List<Pair<String, String>> = emptyList()
)
