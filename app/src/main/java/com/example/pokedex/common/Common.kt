package com.example.pokedex.common

import com.example.pokedex.models.Pokemon

object Common {
    fun findPokemonById(name: String?): Pokemon? {
        for(pokemon in Common.pokemonList)
            if(pokemon.name.equals(name))
                return pokemon
        return null
    }

    var pokemonList:List<Pokemon> = ArrayList()
    val KEY_ENABLE_HOME = "position"
}