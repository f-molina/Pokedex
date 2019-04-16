package com.example.pokedex.api

import com.example.pokedex.models.Pokedex
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    fun listPokemon(@Query("limit") limit:Int, @Query("offset") offset:Int):Observable<Pokedex>
}