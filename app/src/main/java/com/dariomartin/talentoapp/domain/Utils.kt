package com.dariomartin.talentoapp.domain

import com.dariomartin.talentoapp.domain.model.Character

fun getInitialLetter(character: Character?): Char {
    var firstLetter: Char? = character?.name?.firstOrNull()
    if (firstLetter == null || firstLetter.isDigit()) {
        firstLetter = '#'
    }
    return firstLetter
}
