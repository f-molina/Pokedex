package com.example.pokedex.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.models.Pokemon
import com.example.pokedex.R
import com.example.pokedex.common.Common
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.pokemon_card_viewer.view.*

class PokemonAdapter(val context: Context, val pokemonList: List<Pokemon>):RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.pokemon_card_viewer, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonList[position])
        var aux:Int= position+1
        Glide.with(holder.itemView.context)
                .load("https://pokeres.bastionbot.org/images/pokemon/$aux.png")
                .into(holder.imgPokemon)

        //click
        holder.setItemClickListener(View.OnClickListener {
            //Toast.makeText(context, "Clicked on" + pokemonList[position], Toast.LENGTH_SHORT).show()
            LocalBroadcastManager.getInstance(context)
                .sendBroadcast(Intent(Common.KEY_ENABLE_HOME)
                    .putExtra("position", position))
        })


        //cardview color switch
        if(position %2 == 1){
            holder.itemView.card_view.setCardBackgroundColor(Color.parseColor("#1e88e5"))
        }else{
            holder.itemView.card_view.setCardBackgroundColor(Color.parseColor("#6ab7ff"))
        }

    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var imgPokemon: ImageView = itemView.findViewById(R.id.pokemon_image)
        fun bind(item: Pokemon) = with(itemView){
            pokemon_name.text = item.name?.capitalize()
            pokemon_id.text = item.id.toString()

        }

        fun setItemClickListener(onclickListener: View.OnClickListener){
            itemView.setOnClickListener{ view -> onclickListener.onClick(view)}
        }
    }
}