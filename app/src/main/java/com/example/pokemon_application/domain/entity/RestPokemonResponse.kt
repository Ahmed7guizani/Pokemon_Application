package com.example.pokemon_application.domain.entity


class RestPokemonResponse {
    private val messages: MutableList<Pokemon?>? = null
    private val status: String? = null

    fun getInformation(): MutableList<Pokemon?>? {
        return messages
    }

    fun getStatus(): String? {
        return status
    }

}