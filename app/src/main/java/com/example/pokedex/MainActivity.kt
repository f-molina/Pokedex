package com.example.pokedex

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.adapters.PokemonAdapter
import com.example.pokedex.network.NetworkUtils
import com.example.pokedex.pojo.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pokemon_card_viewer.*
import kotlinx.android.synthetic.main.pokemon_list_fragment.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var pokemon: MutableList<Pokemon>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FetchPokemonTask().execute()
    }

    fun initRecycler(){
        viewManager = GridLayoutManager(this, 1)
        viewAdapter = PokemonAdapter(pokemon)

        rv_pokemon_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private inner class FetchPokemonTask : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String? {


            // val ID = pokemonNumbers[0]

            val prueba = NetworkUtils.Factory

            //Por que?? !!
            val pokeAPI: URL = prueba.buildUrl()!!

            return try {
                prueba.getResponseFromHttpUrl(pokeAPI)

            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }
        }

        override fun onPostExecute(pokemonInfo: String?) {
            if (pokemonInfo != null || pokemonInfo != "") {
                Log.d("Info", pokemonInfo + "")

                val jObj = JSONObject(pokemonInfo)
                val jObjresult = jObj.getJSONArray("results")
                pokemon = MutableList(10) { i ->
                    Log.d("prueba", i.toString())
                    val jObjresultobj = JSONObject(jObjresult[i].toString())

                    Pokemon(jObjresultobj.getString("name"), jObjresultobj.getString("url"))

                }
                initRecycler()
            } else {
                pokemon_name.text = pokemonInfo
            }
        }
    }
}
