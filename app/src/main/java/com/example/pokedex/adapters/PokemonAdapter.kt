package com.example.pokedex.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.pojo.Pokemon
import kotlinx.android.synthetic.main.pokemon_card_viewer.view.*

class PokemonAdapter(var pokemon: List<Pokemon>): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_card_viewer, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pokemon.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        var p:Pokemon = pokemon.get(position)
        holder.bind(pokemon[position])
        var aux:Int= position+1
        Glide.with(holder.itemView.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$aux.png")
                .into(holder.fotoImageView)

        if(position %2 == 1){
            holder.itemView.card_view.setBackgroundColor(Color.parseColor("#1e88e5"))
        }else{
            holder.itemView.card_view.setBackgroundColor(Color.parseColor("#6ab7ff"))
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var fotoImageView: ImageView = itemView.findViewById(R.id.pokemon_image)
        fun bind(item: Pokemon) = with(itemView){
            pokemon_name.text = item.name

        }
    }
}