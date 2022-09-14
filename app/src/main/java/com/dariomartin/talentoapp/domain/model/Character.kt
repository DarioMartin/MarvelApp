package com.dariomartin.talentoapp.domain.model

data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val comics: List<Resource> = emptyList(),
    val series: List<Resource> = emptyList(),
    val events: List<Resource> = emptyList()
)