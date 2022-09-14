package com.dariomartin.marvelapp.domain

import com.dariomartin.marvelapp.domain.model.Character

fun getInitialLetter(character: Character?): Char {
    var firstLetter: Char? = character?.name?.firstOrNull()
    if (firstLetter == null || !firstLetter.isLetter()) {
        firstLetter = '#'
    }
    return firstLetter
}
