package com.dariomartin.marvelapp

import com.dariomartin.marvelapp.domain.model.Character

fun createCharacters(count: Int): List<Character> {
    val characters = mutableListOf<Character>()
    for (i in 1..count) {
        characters.add(createCharacter(i))
    }
    return characters
}

fun createCharacter(id: Int): Character {
    return Character(
        id = id,
        name = "${id}_Character",
        description = "Description for character $id",
        imageUrl = "https://www.domain.com/imageUrl/character_$id.jpg"
    )
}
