package com.example.pokedex


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pokedex.common.Common
import com.example.pokedex.models.Pokemon
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*
import kotlinx.android.synthetic.main.pokemon_card_viewer.*

class PokemonDetail : Fragment() {

    private lateinit var pokemonImg: ImageView
    private lateinit var pokeName: TextView

    companion object {
        private var instance: PokemonDetail?= null

        fun getInstance():PokemonDetail{
            if(instance == null)
                instance = PokemonDetail()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        val pokemon: Pokemon?
        if(arguments!!.getString("num") == null)
            pokemon = Common.pokemonList[arguments!!.getInt("position")]
        else
            pokemon = Common.findPokemonById(arguments!!.getString("num"))
        pokemonImg = itemView.findViewById(R.id.pokemon_image)
        pokeName = itemView.findViewById(R.id.name)

        setDetailPokemon(pokemon)

        return itemView
    }

    private fun setDetailPokemon(pokemon: Pokemon?) {
        Glide.with(activity!!)
                .load("https://pokeres.bastionbot.org/images/pokemon/"+ arguments?.getInt("position")!!.plus(1)+".png")
                .into(pokemonImg)

        pokeName.text = pokemon!!.name

    }
}
