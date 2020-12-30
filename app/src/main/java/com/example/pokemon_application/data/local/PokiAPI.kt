package com.example.pokemon_application.data.local

import com.example.pokemon_application.domain.entity.RestPokemonResponse
import retrofit2.Call

import retrofit2.http.GET

interface PokiAPI {
    @GET("pokedex.json")
    fun breedResponse(): Call<RestPokemonResponse>
}