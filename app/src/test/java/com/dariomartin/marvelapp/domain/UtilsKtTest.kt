package com.dariomartin.marvelapp.domain

import com.dariomartin.marvelapp.domain.model.Character
import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class UtilsKtTest {

    @Test
    fun `Test initial letter`() {
        val character = Character(
            id = 1,
            name = "test",
            description = "",
            imageUrl = ""
        )

        val initialLetter: Char = getInitialLetter(character)
        assertThat(initialLetter).isEqualTo('t')
    }

    @Test
    fun `Test initial number`() {
        val character = Character(
            id = 1,
            name = "1_test",
            description = "",
            imageUrl = ""
        )

        val initialLetter: Char = getInitialLetter(character)
        assertThat(initialLetter).isEqualTo('#')
    }

    @Test
    fun `Test initial special character`() {
        val character = Character(
            id = 1,
            name = "_test",
            description = "",
            imageUrl = ""
        )

        val initialLetter: Char = getInitialLetter(character)
        assertThat(initialLetter).isEqualTo('#')
    }

    @Test
    fun `Test initial empty`() {
        val character = Character(
            id = 1,
            name = "",
            description = "",
            imageUrl = ""
        )

        val initialLetter: Char = getInitialLetter(character)
        assertThat(initialLetter).isEqualTo('#')
    }

}